package com.jaf.examples.elasticsearch.node;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.google.common.collect.Maps;
import com.google.gson.Gson;

public class IndexTest {

	public static void main(String[] args) throws UnknownHostException {
		Client client = TransportClient.builder().build()
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(Config.ES_IP), 9300));
//		indexGet(client);
		indexCreate(client);
//		indexDelete(client,"AVPmcmhdem-ZMWF0VMHm");

	}
	
	public static void indexGet(Client client ) {
		SearchResponse res = null;
		res = client.prepareSearch("search_test").setTypes("article").get();
		
		System.out.println(res);
		// on shutdown
		client.close();
	}
	
	public static void indexCreate(Client client ) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("title", "title-01");
		map.put("body", "body test");
		map.put("publish", "publish_date");
		String s = new Gson().toJson(map);
		IndexResponse res = null;
		res = client.prepareIndex("api_test","type_test").setSource(s).execute().actionGet();
		System.out.println(res);
		// on shutdown
		client.close();
	}
	
	public static void indexDelete(Client client, String id ) {

		DeleteResponse res = null;
		res = client.prepareDelete("api_test","type_test", id).execute().actionGet();
		System.out.println(res.isFound());
		// on shutdown
		client.close();
	}
	
	

}
