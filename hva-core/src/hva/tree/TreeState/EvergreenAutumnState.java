package hva.tree.TreeState;

import hva.season.Season;
import hva.tree.Tree;

public class EvergreenAutumnState implements TreeState {
    @Override
    public TreeState advanceSeason(Tree tree) {
        if (tree.getSeason() == Season.Winter)
            tree.incrementAge();
        return new EvergreenWinterState();
    }

    @Override
    public String getBioCycle() {
        return "COMFOLHAS";
    }

    @Override
    public int getSeasonalWork() {
        return 1;
    }
}
