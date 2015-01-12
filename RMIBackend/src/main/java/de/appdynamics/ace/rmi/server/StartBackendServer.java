package de.appdynamics.ace.rmi.server;

import com.appdynamics.ace.util.cli.api.api.AbstractCommand;
import com.appdynamics.ace.util.cli.api.api.CommandException;
import com.appdynamics.ace.util.cli.api.api.OptionWrapper;
import org.apache.commons.cli.Option;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan.marx on 27.11.14.
 */
public class StartBackendServer extends AbstractCommand {
    @Override
    protected List<Option> getCLIOptionsImpl() {

        List<Option> options = new ArrayList<Option>();
        Option o;
        options.add(o = new Option("port",true,"Portnumber for registry"));
        o.setRequired(true);
        return options;
    }

    @Override
    protected int executeImpl(OptionWrapper optionWrapper) throws CommandException {
        int port = Integer.parseInt(optionWrapper.getOptionValue("port"));

        try {
            System.out.println("Create Registry on port "+port);
            LocateRegistry.getRegistry();
            EchoBusinessImpl h = new EchoBusinessImpl();

            Naming.bind("server.EchoBusiness", h);


            System.out.println("RMI Ready.");

        } catch (RemoteException e) {
            new CommandException("couldn't create Registry",e);
        }
        catch (MalformedURLException e) {
            new CommandException("couldn't bind to Registry",e);
        } catch (AlreadyBoundException e) {
            new CommandException("couldn't bind to Registry",e);
        }

        return 0;
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start RMI Server Implementation";
    }
}
