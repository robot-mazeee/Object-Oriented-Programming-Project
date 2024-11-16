package hva.habitat;

public enum Influence {
    POS(20),
    NEU(0),
    NEG(-20);

    private int _influence;

    private Influence(int value){
        _influence = value;
    }

    public int getValue() {
        return _influence;
    }
}
