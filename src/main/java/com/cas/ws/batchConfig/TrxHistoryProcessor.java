package com.cas.ws.batchConfig;


import com.cas.ws.io.entity.TrxHistory;
import org.springframework.batch.item.ItemProcessor;

public class TrxHistoryProcessor implements ItemProcessor<TrxHistory, TrxHistory> {

	@Override
	public TrxHistory process(TrxHistory trxHistory) throws Exception {
		//trxHistory.setID(UUID.randomUUID().toString());
		return trxHistory;
	}
}