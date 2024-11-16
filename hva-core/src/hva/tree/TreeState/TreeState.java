package hva.tree.TreeState;

import hva.tree.Tree;

public interface TreeState {

    public abstract TreeState advanceSeason(Tree tree);
    public String getBioCycle();
    public int getSeasonalWork();
}
