package hva.tree.TreeState;

import hva.season.Season;
import hva.tree.Tree;

public class EvergreenSummerState implements TreeState {
    @Override
    public TreeState advanceSeason(Tree tree) {
        if (tree.getSeason() == Season.Autumn)
            tree.incrementAge();
        return new EvergreenAutumnState();
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
