package org.example.foodbudgetbackendspring.product;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
class ProductDensityService {
    private static final float STANDARD = 1.0f;
    private static final float OIL = 0.92f;
    private static final float SAUCE = 1.15f;
    private static final float SYRUP = 1.35f;

    private static final Map<Float, List<String>> MAPPING = new LinkedHashMap<>();

    static {
        MAPPING.put(SYRUP, List.of(
                "miód", "syrop", "klonow", "agawa", "agawy",
                "melasa", "balsamicz", "balsamico", "karmel",
                "kajmak", "glukoza", "fruktoza"
        ));

        MAPPING.put(SAUCE, List.of(
                "sos", "sojowy", "rybny", "ostrygowy", "worcestershire",
                "hoisin", "teriyaki", "ketchup", "kecup", "maggi",
                "sriracha", "tabasco", "tamari", "dressing", "dip"
        ));

        MAPPING.put(OIL, List.of(
                "olej", "oliwa", "tłuszcz", "ghee", "masło klarowane",
                "mct", "smalec", "frytura", "margaryna w płynie"
        ));
    }

    public float getDensityByProductName(@Nonnull String name){
        String lowerName = name.toLowerCase();

        return MAPPING.entrySet().stream()
                .filter(
                        entry -> entry.getValue().stream().anyMatch(lowerName::contains)
                )
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(STANDARD);

    }
}
