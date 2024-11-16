package hva.tree.TreeState;

import hva.season.Season;
import hva.tree.Tree;

public class DeciduousWinterState implements TreeState {
    @Override
    public TreeState advanceSeason(Tree tree) {
        if (tree.getSeason() == Season.Spring)
            tree.incrementAge();
        return new DeciduousSpringState();
    }

    @Override
    public String getBioCycle() {
        return "SEMFOLHAS";
    }

    @Override
    public int getSeasonalWork() {
        return 0;
    }
}
