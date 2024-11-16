package hva.tree.TreeState;

import hva.season.Season;
import hva.tree.Tree;

public class EvergreenWinterState implements TreeState {
    @Override
    public TreeState advanceSeason(Tree tree) {
        if (tree.getSeason() == Season.Spring)
            tree.incrementAge();
        return new EvergreenSpringState();
    }

    @Override
    public String getBioCycle() {
        return "LARGARFOLHAS";
    }

    @Override
    public int getSeasonalWork() {
        return 2;
    }
}