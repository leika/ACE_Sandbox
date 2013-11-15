package de.appdynamics.ace.tcp.server;

import com.appdynamics.ace.util.cli.api.api.AbstractCommand;
import com.appdynamics.ace.util.cli.api.api.Command;
import com.appdynamics.ace.util.cli.api.api.CommandException;
import com.appdynamics.ace.util.cli.api.api.OptionWrapper;
import org.apache.commons.cli.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stefan.marx
 * Date: 15.11.13
 * Time: 08:11
 * To change this template use File | Settings | File Templates.
 */
public class StartBackendServer extends AbstractCommand {
    @Override
    protected List<Option> getCLIOptionsImpl() {
        List<Option> opts = new ArrayList<Option>();
        opts.add(new Option("port",true,"Server port number."));

        return opts;
    }

    @Override
    protected int executeImpl(OptionWrapper optionWrapper) throws CommandException {
        ServerHandle serverHandle = new ServerHandle(Integer.parseInt(optionWrapper.getOptionValue("port", "8989")));
        serverHandle.start();
        return 0;
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start the TCP Server";
    }
}
