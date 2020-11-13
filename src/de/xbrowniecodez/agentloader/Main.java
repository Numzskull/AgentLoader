package de.xbrowniecodez.agentloader;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

public class Main extends JavaPlugin {
	private String prefix = "§c[AgentLoader] ";
	public void onEnable() {
		String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
		String pid = nameOfRunningVM.substring(0, nameOfRunningVM.indexOf('@'));
		try {
		    Bukkit.getConsoleSender().sendMessage(prefix + "Attempting to inject agent..");
			VirtualMachine virtualMachine = VirtualMachine.attach(pid);
			try {
				virtualMachine.loadAgent("Agent.jar", null);
			} catch (AgentLoadException | AgentInitializationException e) {
			    Bukkit.getConsoleSender().sendMessage(prefix + "Agent failed to inject!");
				e.printStackTrace();
			}
			Bukkit.getConsoleSender().sendMessage(prefix + "Agent injected successfully!");
			virtualMachine.detach();
		} catch (AttachNotSupportedException | IOException e) {
			e.printStackTrace();
		}
	}

}
