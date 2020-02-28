package by.epam.web.unit6.command;

import by.epam.web.unit6.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        commands.put(CommandName.EDIT_PROFILE, new EditProfileCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.CREATE_USER, new CreateUserCommand());
        commands.put(CommandName.UPDATE_USER, new UpdateUserCommand());
        commands.put(CommandName.LOGOUT, new LogOutCommand());
        commands.put(CommandName.SHOW_TARIFS, new ShowTariffsCommand());
        commands.put(CommandName.UPDATE_BALANCE, new UpdateBalanceCommand());
        commands.put(CommandName.SHOW_USERS, new ShowUsersCommand());
        commands.put(CommandName.ADD_NOTE, new AddNoteCommand());
        commands.put(CommandName.DELETE_NOTE, new DeleteNoteCommand());
        commands.put(CommandName.CHANGE_BALANCE, new ChangeBalanceCommand());
        commands.put(CommandName.CHANGE_STATUS, new UpdateStatusCommand());
        commands.put(CommandName.DELETE_USER, new DeleteUserCommand());
        commands.put(CommandName.ADD_TARIF, new AddTarifCommand());
        commands.put(CommandName.EDIT_TARIF, new EditTarifCommand());
        commands.put(CommandName.DELETE_TARIF, new DeleteTarifCommand());
        commands.put(CommandName.SHOW_USER_TARIF, new ShowUserTariffsCommand());
        commands.put(CommandName.PAYMENT, new PaymentCommand());
        commands.put(CommandName.BLOCK, new BlockCommand());
        commands.put(CommandName.TARIFF_PAGINATION, new TariffPaginationCommand());
        commands.put(CommandName.STATISTIC, new StatisticCommand());
        commands.put(CommandName.USER_PAGINATION, new UserPaginationCommand());

    }

    public static CommandProvider getInstance() {
        return instance;
    }
    public Command getCommand (String commandName) {
        CommandName name = CommandName.valueOf(commandName.toUpperCase());
        Command command;
        if (null != name) {
            command = commands.get(name);
        }else {
            command = commands.get(CommandName.NO_SUCH_COMMAND);
        }
        return command;
    }

}


