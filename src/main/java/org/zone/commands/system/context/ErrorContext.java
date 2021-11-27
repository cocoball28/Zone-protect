package org.zone.commands.system.context;

import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;

public record ErrorContext(ArgumentCommand command, int argumentFailedAt,
                           CommandArgument<?> argument, String error) {

}
