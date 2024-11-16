package hva.tree.TreeState;

import hva.tree.Tree;
import hva.season.Season;

public class DeciduousAutumnState implements TreeState {
    @Override
    public TreeState advanceSeason(Tree tree) {
        if (tree.getSeason() == Season.Winter)
            tree.incrementAge();
        return new DeciduousWinterState();
    }

    @Override
    public String getBioCycle() {
        return "LARGARFOLHAS";
    }

    @Override
    public int getSeasonalWork() {
        return 5;
    }
}
