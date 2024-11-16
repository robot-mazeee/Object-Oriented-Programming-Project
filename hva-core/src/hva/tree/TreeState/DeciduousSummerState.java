package hva.tree.TreeState;

import hva.season.Season;
import hva.tree.Tree;

public class DeciduousSummerState implements TreeState {
    @Override
    public TreeState advanceSeason(Tree tree) {
        if (tree.getSeason() == Season.Autumn)
            tree.incrementAge();
        return new DeciduousAutumnState();
    }

    @Override
    public String getBioCycle() {
        return "COMFOLHAS";
    }

    @Override
    public int getSeasonalWork() {
        return 2;
    }
}
