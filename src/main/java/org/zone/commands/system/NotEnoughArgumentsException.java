package org.zone.commands.system;

import java.io.IOException;

public class NotEnoughArgumentsException extends IOException {
    public NotEnoughArgumentsException(Iterable<String> missingArguments) {
        super("Not enough arguments. Missing: " + String.join(",", missingArguments));
    }
}
