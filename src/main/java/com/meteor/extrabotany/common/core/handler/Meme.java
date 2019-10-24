package com.meteor.extrabotany.common.core.handler;

import org.apache.logging.log4j.Logger;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.core.config.ConfigHandler;

public class Meme {
	
	public static void init() {
		Logger logger = ExtraBotany.logger;
		if (ConfigHandler.ENABLE_FEATURES) {
			logger.info("=============================================================================================");
			logger.info("*  _______   ___  ___   ___________    _______         __                                   *");
			logger.info("* /\"     \"| |\"  \\/\"  | (\"     _   \")  /\"      \\       /\"\"\\                                  *");
			logger.info("*(: ______)  \\   \\  /   )__/  \\\\__/  |:        |     /    \\                                 *");
			logger.info("* \\/    |     \\\\  \\/       \\\\_ /     |_____/   )    /' /\\  \\                                *");
			logger.info("* // ___)_    /\\.  \\       |.  |      //      /    //  __'  \\                               *");
			logger.info("*(:      \"|  /  \\   \\      \\:  |     |:  __   \\   /   /  \\\\  \\                              *");
			logger.info("* \\_______) |___/\\___|      \\__|     |__|  \\___) (___/    \\___)                             *");
			logger.info("*           _______       ______     ___________        __       _____  ___    ___  ___       *");
			logger.info("*          |   _  \"\\     /    \" \\   (\"     _   \")      /\"\"\\     (\\\"   \\|\"  \\  |\"  \\/\"  | 	  *");
			logger.info("*          (. |_)  :)   // ____  \\   )__/  \\\\__/      /    \\    |.\\\\   \\    |  \\   \\  /  	  *");
			logger.info("*          |:     \\/   /  /    ) :)     \\\\_ /        /' /\\  \\   |: \\.   \\\\  |   \\\\  \\/   	  *");
			logger.info("*          (|  _  \\\\  (: (____/ //      |.  |       //  __'  \\  |.  \\    \\. |   /   /    	  *");
			logger.info("*          |: |_)  :)  \\        /       \\:  |      /   /  \\\\  \\ |    \\    \\ |  /   /     	  *");
			logger.info("*          (_______/    \\\"_____/         \\__|     (___/    \\___) \\___|\\____\\) |___/      	  *");
			logger.info("=============================================================================================");
			logger.info("*Thank you for installing ExtraBotany!                                                      *");
			logger.info("*Owner: ExtraMeteorP                                                                        *");
			logger.info("*Artist: MalayP, Gloridifice                                                                *");
			logger.info("*No more anime girl                                                                         *");
			logger.info("*Yes this is a spam                                                                         *");
			logger.info("*If you want to disable it, check your config settings                                      *");
			logger.info("=============================================================================================");
			logger.info(" .____         .                 __   __         .                        .___ ");
			logger.info(" /      _  .- _/_   .___    ___  |    |    ___  _/_     ___    __.  .___  /   \\");
			logger.info(" |__.    \\,'   |    /   \\  /   ` |\\  /|  .'   `  |    .'   ` .'   \\ /   \\ |,_-'");
			logger.info(" |       /\\    |    |   ' |    | | \\/ |  |----'  |    |----' |    | |   ' |    ");
			logger.info(" /----/ /  \\   \\__/ /     `.__/| /    /  `.___,  \\__/ `.___,  `._.' /     /    ");
			logger.info("                                                                               ");
			logger.info("					 .___                                     .                _    ");
			logger.info("					 /   \\ .___    ___    ____   ___  , __   _/_     ___    ___/  ");
			logger.info("					 |,_-' /   \\ .'   `  (     .'   ` |'  `.  |    .'   `  /   |  ");
			logger.info("					 |     |   ' |----'  `--.  |----' |    |  |    |----' ,'   |  ");
			logger.info("					 /     /     `.___, \\___.' `.___, /    |  \\__/ `.___, `___,'  ");
			logger.info("																			   `   ");
			logger.info("=============================================================================================");
			if(!PersistentVariableHandler.contributors.isEmpty()) {
				logger.info("*SPECIAL THANKS LIST                                                                        *");
				for(int i = 0; i < PersistentVariableHandler.contributors.size(); i++)
					logger.info("*"+String.format("%-91s", PersistentVariableHandler.contributors.get(i))+"*");
				logger.info("=============================================================================================");
			}
		}
	}

}
