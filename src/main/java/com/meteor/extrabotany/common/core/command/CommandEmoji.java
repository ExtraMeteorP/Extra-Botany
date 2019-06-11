package com.meteor.extrabotany.common.core.command;

import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandEmoji extends CommandBase{

	@Override
	public String getName() {
		return "emoji";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.emoji.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 2){
            throw new WrongUsageException("commands.emoji.usage");
        }else if(args.length == 1){
        	if(args[0].matches("list"))
        		for(int i = 0; i < LibMisc.EMOJI.length; i++)
        			sender.sendMessage(new TextComponentTranslation(String.valueOf(i+1)+ " " + LibMisc.EMOJI[i]));
        	else{
	            String emoji = args.length > 0 ? args[0] : "1";     
	            sendMessage(server, sender, LibMisc.EMOJI[Integer.valueOf(emoji) - 1]);
        	}
        }else if(args.length == 2){
        	String command = args[0];
        	String emoji = args[1];
        	if(command.matches("test"))
        		sender.sendMessage(new TextComponentTranslation(LibMisc.EMOJI[Integer.valueOf(emoji) - 1]));
        }	
	}
	
	public void sendMessage(MinecraftServer server, ICommandSender sender, String s){
		server.getPlayerList().sendMessage(new TextComponentTranslation("<" + sender.getName() + "> " + s));
	}
}
