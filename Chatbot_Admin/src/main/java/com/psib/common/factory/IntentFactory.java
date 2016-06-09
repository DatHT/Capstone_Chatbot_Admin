/**
 * 
 */
package com.psib.common.factory;

import org.springframework.stereotype.Component;

/**
 * @author DatHT
 * Jun 8, 2016
 * @Email: datht0601@gmail.com
 */

@Component
public class IntentFactory extends AbstractFactory {

	public IntentFactory() {
		super("/intents");
	}
}
