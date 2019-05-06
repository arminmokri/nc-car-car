/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.File;
import java.io.FileReader;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.configuration2.INIConfiguration;

/**
 *
 * @author armin
 */
public class Config {

    private Options Options;
    private File Config;
    private Server Server;

    public Config(String[] args) {
        this.Options = new Options();

        this.setOptions();

        try {
            this.setArg(args);
        } catch (ParseException exception) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("nc-car-car", Options);
            System.err.println(exception.getMessage());
            System.exit(1);
        }

        try {
            this.setConfig();
        } catch (ConfigurationException exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }

    }

    private void setOptions() {
        // config file
        Option option_config = new Option("c", "config", true, "config file path");
        option_config.setRequired(true);
        Options.addOption(option_config);
        // others
    }

    private void setArg(String[] args) throws ParseException {
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse(this.Options, args);
        this.Config = new File(commandLine.getOptionValue('c'));
    }

    private void setConfig() throws ConfigurationException, Exception {
        INIConfiguration iNIConfiguration = new INIConfiguration();
        iNIConfiguration.read(new FileReader(this.Config));

        // set server
        this.Server = new Server();
        this.Server.setPort(iNIConfiguration.getSection("server").getInt("port"));
        this.Server.setHostAddress(iNIConfiguration.getSection("server").getString("host"));

    }

    public Server getServer() {
        return Server;
    }

}