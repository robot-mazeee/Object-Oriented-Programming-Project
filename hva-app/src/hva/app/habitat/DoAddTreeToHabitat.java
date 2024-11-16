package hva.app.habitat;

import java.io.IOException;

import hva.Hotel;
import hva.exceptions.DuplicateTreeException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownTreeException;
import hva.exceptions.UnrecognizedEntryException;
import hva.tree.Tree;
import hva.app.exceptions.DuplicateTreeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownTreeKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoAddTreeToHabitat extends Command<Hotel> {

    DoAddTreeToHabitat(Hotel receiver) {
        super(Label.ADD_TREE_TO_HABITAT, receiver);
        addStringField("habitatId", Prompt.habitatKey());
        addStringField("treeId", Prompt.treeKey());
        addStringField("treeName", Prompt.treeName());
        addStringField("treeAge", Prompt.treeAge());
        addStringField("treeDifficulty", Prompt.treeDifficulty());
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");
        String treeId = stringField("treeId");
        String treeName = stringField("treeName");
        String treeAge = stringField("treeAge");
        String treeDifficulty = stringField("treeDifficulty");
        String treeType;
        do {
            treeType = Form.requestString(Prompt.treeType());
        } while (!treeType.equals("CADUCA") && !treeType.equals("PERENE"));

        try {
            Tree tree = _receiver.registerTree(new String[] {"ÁRVORE", treeId,
            treeName, treeAge, treeDifficulty, treeType});
            try {
                _receiver.addTreeToHabitat(habitatId, treeId);
                _display.popup(tree);
            } catch (UnknownHabitatException e) {
                throw new UnknownHabitatKeyException(habitatId);
            }
        } catch (DuplicateTreeException e) {
            throw new DuplicateTreeKeyException(treeId);
        } catch (IOException | UnrecognizedEntryException e) {
            e.printStackTrace();
        }
    }
}
