package seedu.address.logic.commands.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.util.CommandUtil.getCommandResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.ui.tabs.TabName;

public class CommandUtilTest {

    private static final String FEEDBACK = "feedback";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void getCommandResult4Parameters_dontSwitchTab_success() {
        CommandResult expected = new CommandResult(FEEDBACK);
        CommandResult result = getCommandResult(model, FEEDBACK, TabName.COMPANY, TabName.COMPANY,
            Index.fromOneBased(1));
        assertEquals(expected, result);
    }

    @Test
    public void getCommandResult4Parameters_switchTabCompanyToProfile_success() {
        CommandResult expected = new CommandResult(FEEDBACK, false, false, true, true);
        CommandResult result = getCommandResult(model, FEEDBACK, TabName.COMPANY, TabName.PROFILE,
            Index.fromOneBased(1));
        assertEquals(expected, result);
    }

    @Test
    public void getCommandResult4Parameters_switchTabCompanyToApplication_success() {
        CommandResult expected = new CommandResult(FEEDBACK, false, false, true, true);
        CommandResult result = getCommandResult(model, FEEDBACK, TabName.COMPANY, TabName.APPLICATION,
            Index.fromOneBased(1));
        assertEquals(expected, result);
    }

    @Test
    public void getCommandResult4Parameters_switchDisplay_success() {
        CommandResult expected = new CommandResult(FEEDBACK);
        model.setCompanyViewIndex(Index.fromOneBased(3));
        CommandResult result = getCommandResult(model, FEEDBACK, TabName.COMPANY, TabName.COMPANY,
            Index.fromOneBased(3));
        assertEquals(expected, result);
    }
}
