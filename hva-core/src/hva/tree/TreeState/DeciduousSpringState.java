package hva.tree.TreeState;

import hva.season.Season;
import hva.tree.Tree;

public class DeciduousSpringState implements TreeState {
    @Override
    public TreeState advanceSeason(Tree tree) {
        if (tree.getSeason() == Season.Summer)
            tree.incrementAge();
        return new DeciduousSummerState();
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
