package hva.tree;
import hva.season.Season;
import hva.tree.TreeState.TreeState;

import java.io.Serializable;

public abstract class Tree implements Serializable {
    /** Tree's id. */
    private String _id;

    /** Tree's name. */
    private String _name;

    /** Tree's age. */
    private int _age;

    /** Tree's base cleaning difficulty. */
    private int _baseCleaningDifficulty;

    /** Season of tree creation. */
    private Season _season;

    private TreeState _treeState;

    /**
     * @param id
     * @param name
     * @param age
     * @param difficulty
     * @param season
     */
    public Tree(String id, String name, int age, int difficulty, Season season, TreeState initialState){
        _id = id;
        _name = name;
        _age = age;
        _baseCleaningDifficulty = difficulty;
        _season = season;
        _treeState = initialState;
    }

    /**
     * @return tree's id.
     */
    public String getId(){
        return _id;
    }

    /**
     * @return tree's name.
     */
    public String getName(){
        return _name;
    }

    /**
     * @return tree's age.
     */
    public int getAge(){
        return _age;
    }

    /**
     * @return tree's base cleaning difficulty.
     */
    public int getBaseCleaningDifficulty(){
        return _baseCleaningDifficulty;
    }

    /**
     * @return current season.
     */
    public Season getSeason(){
        return _season;
    }

    public void incrementAge() {
        _age += 1;
    }
    
    public void advanceSeason() {
        _treeState = _treeState.advanceSeason(this);
    }

    public String getBioCycle() {
        return _treeState.getBioCycle();
    }

    public int getSeasonalWork() {
        return _treeState.getSeasonalWork();
    }


    public abstract float getCleaningDifficulty();
}