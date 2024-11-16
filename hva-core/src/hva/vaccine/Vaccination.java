package hva.vaccine;

import hva.employee.*;

import java.util.Map;
import hva.species.*;

public class Vaccination {
    /** Vaccinated animal. */
    private Species _species;

    /** Vaccine used. */
    private Vaccine _vaccine;

    /** Vet that vaccinated. */
    private Vet _vet;

    /** Damage done by the vaccination. */
    private Damage _damage;

    public Vaccination(Species species, Vaccine vaccine, Vet vet){
        _species = species;
        _vaccine = vaccine;
        _vet = vet;
        _damage = calculateDamage();
    }

    public Damage getDamage(){
        return _damage;
    }

    public Damage calculateDamage() {
        Map<String, Species> speciesList = _vaccine.getSpeciesList();
        if (speciesList.containsValue(_species)){
            return Damage.NORMAL;
        }
        else {
            int max_damage = 0;
            for (Species s : speciesList.values()) {
                int damage = differentChars(s, _species);
                if (damage > max_damage){
                    max_damage = damage;
                }
            }
            switch (max_damage) {
                case 0: return Damage.CONFUSÃO;
                case 1, 2, 3, 4: return Damage.ACIDENTE;        
                default: return Damage.ERRO;
            }
        }
    }

    private int differentChars(Species s1, Species s2) {
        String name1 = s1.getName().toLowerCase();
        String name2 = s2.getName().toLowerCase();
        String smallName = (name1.length() <= name2.length()) ? name1 : name2;
        String bigName = (name1.length() > name2.length()) ? name1 : name2;

        for (int i = 0; i < smallName.length(); i++) {
            bigName = bigName.replaceFirst(String.valueOf(smallName.charAt(i)), "");
        }
        return bigName.length();
    }

    @Override
    public String toString(){
        return "REGISTO-VACINA|" + _vaccine.getId() + '|' + _vet.getId() + '|' 
        + _species.getId();
    }
}
