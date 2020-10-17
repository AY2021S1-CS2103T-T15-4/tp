package seedu.address.logic.commands.util;

import static seedu.address.model.util.ItemUtil.APPLICATION_NAME;
import static seedu.address.model.util.ItemUtil.COMPANY_NAME;
import static seedu.address.model.util.ItemUtil.PROFILE_NAME;
import static seedu.address.ui.tabs.TabName.APPLICATION;
import static seedu.address.ui.tabs.TabName.COMPANY;
import static seedu.address.ui.tabs.TabName.PROFILE;

import java.util.List;
import java.util.function.Consumer;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.ApplicationItem;
import seedu.address.model.company.CompanyItem;
import seedu.address.model.profile.ProfileItem;
import seedu.address.ui.tabs.TabName;

/**
 * Encapsulates common / shared execution processes between commands.
 */
public class CommandUtil {

    public static CompanyItem getCompany(Model model, Index companyIndex) throws CommandException {
        List<CompanyItem> lastShownList = model.getCompanyList().getFilteredItemList();

        if (companyIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX, COMPANY_NAME));
        }

        return lastShownList.get(companyIndex.getZeroBased());
    }

    public static ApplicationItem getApplication(Model model, Index applicationIndex) throws CommandException {
        List<ApplicationItem> lastShownList = model.getApplicationList().getFilteredItemList();

        if (applicationIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX, APPLICATION_NAME));
        }

        return lastShownList.get(applicationIndex.getZeroBased());
    }

    public static ProfileItem getProfileItem(Model model, Index profileItemIndex) throws CommandException {
        List<ProfileItem> lastShownList = model.getProfileList().getFilteredItemList();

        if (profileItemIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX, PROFILE_NAME));
        }

        return lastShownList.get(profileItemIndex.getZeroBased());
    }

    /**
     * Gets the feedback message of the operation result for display and indicates whether tabs need to be switched or
     * not.
     *
     * @param model Model of application.
     * @param message Feedback message of the operation result for display.
     * @param tabName The tab that is being switched to.
     * @return Feedback message of the operation result for display.
     */
    public static CommandResult getCommandResult(Model model, String message, TabName tabName) {
        assert model != null && message != null && tabName != null;
        if (model.getTabName() != tabName) {
            model.setTabName(tabName);
            return new CommandResult(message, false, false, true, true);
        } else {
            return new CommandResult(message);
        }
    }

    /**
     * Gets the feedback message of the operation result for display and indicates whether tabs and display view.
     * need to be switched or not.
     *
     * @param model Model of application.
     * @param message Feedback message of the operation result for display.
     * @param currentTabName The current tab of the application.
     * @param changedTabName The tab that is being switched to.
     * @param index The desired index of the tab that is being switch to.
     * @return Feedback message of the operation result for display.
     */
    public static CommandResult getCommandResult(Model model, String message, TabName currentTabName,
        TabName changedTabName, Index index) {

        assert model != null && message != null && currentTabName != null && changedTabName != null && index != null;

        handleDeleteDisplaySwitchIndex(model, changedTabName, index);
        if (currentTabName != changedTabName) {
            model.setTabName(changedTabName);
            return new CommandResult(message, false, false, true, true);
        } else {
            return new CommandResult(message);
        }
    }

    /**
     * Checks which tab's display needs to be switch.
     *
     * @param model Model of application.
     * @param tabName The tab that is being switched to.
     * @param index The desired index of the tab that is being switch to.
     */
    private static void handleDeleteDisplaySwitchIndex(Model model, TabName tabName, Index index) {
        Index currentIndex;
        assert (tabName.equals(COMPANY) || tabName.equals(APPLICATION) || tabName.equals(PROFILE));

        switch (tabName) {
        case COMPANY:
            currentIndex = model.getCompanyViewIndex();
            handleViewIndex(model::setCompanyViewIndex, currentIndex, index);
            break;
        case APPLICATION:
            currentIndex = model.getApplicationViewIndex();
            handleViewIndex(model::setApplicationViewIndex, currentIndex, index);
            break;
        case PROFILE:
            currentIndex = model.getProfileViewIndex();
            handleViewIndex(model::setProfileViewIndex, currentIndex, index);
            break;
        default:
            assert false;
        }
    }

    /**
     * Checks whether there is a need for a switch in the view index.
     *
     * @param changeViewIndex Handles the switching of the view index in the respective tab.
     * @param currentIndex The current view index in the respective tab.
     * @param newIndex The new view index to be switched to in the respective tab.
     */
    private static void handleViewIndex(Consumer<Index> changeViewIndex, Index currentIndex, Index newIndex) {
        if (currentIndex.getOneBased() >= newIndex.getOneBased()) {
            // currentIndex have to minus 1
            int shiftIndex = currentIndex.getOneBased();
            if (!(shiftIndex - 1 == 0)) {
                changeViewIndex.accept(Index.fromOneBased(shiftIndex - 1));
            }
        }
    }

}
