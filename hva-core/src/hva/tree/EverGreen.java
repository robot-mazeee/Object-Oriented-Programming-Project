package hva.tree;

import hva.season.Season;
import hva.tree.TreeState.TreeState;

public class EverGreen extends Tree {
    /**
     * @param id
     * @param name
     * @param age
     * @param difficulty
     */
    public EverGreen(String id, String name, int age, int difficulty, Season season, TreeState initialState) {
        super(id, name, age, difficulty, season, initialState);
    }

    public float getCleaningDifficulty() {
        return getBaseCleaningDifficulty() * super.getSeasonalWork() * (float) Math.log(getAge()+1);
    }

    @Override
    public String toString(){
        return "ÁRVORE" + "|" + getId() + "|" + getName() + "|" + getAge()
        + "|" + getBaseCleaningDifficulty() + "|PERENE|" + 
        getBioCycle();
    }
}
