package com.jaf.examples.akka.cluster.simple;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年5月22日
 * @since 1.0
 */
public class SimpleClusterApp {
	
	public static void main(String[] args) {
		if (args.length == 0)
			startup(new String[] { "2551", "2552", "0" });
		else
			startup(args);
	}
	
	public static void startup(String[] ports) {
		for (String port : ports) {
			// Override the configuration of the port
			Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
					.withFallback(ConfigFactory.load());
					
			// Create an Akka system
			ActorSystem system = ActorSystem.create("ClusterSystem", config);
			
			// Create an actor that handles cluster domain events
			system.actorOf(Props.create(SimpleClusterListener.class), "clusterListener");
		}
	}
	
}
