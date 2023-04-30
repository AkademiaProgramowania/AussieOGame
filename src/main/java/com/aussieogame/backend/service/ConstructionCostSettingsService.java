package com.aussieogame.backend.service;

import com.aussieogame.backend.exception.RequestedActionNotAllowedException;
import com.aussieogame.backend.model.dao.impl.ConstructionCostSettings;
import com.aussieogame.backend.model.dao.impl.Resources;
import com.aussieogame.backend.repo.ConstructionCostSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConstructionCostSettingsService {
    private final ConstructionCostSettingsRepository repo;

    public Resources findByNameAndLevel(String name, int level) {
        return repo.findByItemNameIgnoreCase(name)
                .map(settings -> calculateCostAtLevel(settings, level, name))
                .orElseThrow(supplyNotConfiguredException(name));
    }

    private Resources calculateCostAtLevel(ConstructionCostSettings costSettings, int levelToCalculate, String name) {
        Resources baseCost = getBaseCostOrThrow(costSettings, name);

        if (levelToCalculate <= 1) {
            return baseCost;
        }

        Resources linearIncrement = getNonNullIncrement(costSettings);
        double exponentialDollarsFactor = getExponentialFactorOrOne(costSettings.getExponentialDollarsFactor());
        double exponentialEucalyptusFactor = getExponentialFactorOrOne(costSettings.getExponentialDollarsFactor());

        return applyCostIncreasesUpToLevel(
                baseCost,
                linearIncrement,
                exponentialDollarsFactor,
                exponentialEucalyptusFactor,
                levelToCalculate
        );
    }

    private Resources getBaseCostOrThrow(ConstructionCostSettings costSettings, String name) {
        if (costSettings == null) {
            throw supplyNotConfiguredException(name).get();
        }

        return costSettings.getBaseCost();
    }

    private Resources applyCostIncreasesUpToLevel(Resources baseCost, Resources linear, double expDollars,
                                                  double expEucalyptus, int level) {
        long dollars = baseCost.getDollars();
        long eucalyptus = baseCost.getEucalyptus();

        for (int i = 2; i <= level; ++i) {
            dollars = multiplyThenAdd(dollars, expDollars, linear.getDollars());
            eucalyptus = multiplyThenAdd(eucalyptus, expEucalyptus, linear.getEucalyptus());
        }

        return new Resources(dollars, eucalyptus);
    }

    private Resources getNonNullIncrement(ConstructionCostSettings costSettings) {
        return Objects.requireNonNullElse(
                costSettings.getLinearIncrement(),
                new Resources(0L, 0L)
        );
    }

    private double getExponentialFactorOrOne(Double presetFactor) {
        if (presetFactor == null || presetFactor == 0.0) {
            return 1.0;
        }
        return presetFactor;
    }


    private long multiplyThenAdd(long initial, double multiplier, long increment) {
        return Math.round(initial * multiplier) + increment;
    }

    private Supplier<RequestedActionNotAllowedException> supplyNotConfiguredException(
            String constructionName
    ) {
        String msg = """
                Requested construction of type:
                "%s"
                is not configured properly in the ConstructionCostSettings
                (either missing completely or has a null base cost).
                """.formatted(constructionName);
        return () -> new RequestedActionNotAllowedException(msg);
    }
}
