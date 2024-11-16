package hva.tree.TreeState;

import hva.season.Season;
import hva.tree.Tree;

public class EvergreenSpringState implements TreeState {
    @Override
    public TreeState advanceSeason(Tree tree) {
        if (tree.getSeason() == Season.Summer)
            tree.incrementAge();
        return new EvergreenSummerState();
    }

    @Override
    public String getBioCycle() {
        return "GERARFOLHAS";
    }

    @Override
    public int getSeasonalWork() {
        return 1;
    }
}
