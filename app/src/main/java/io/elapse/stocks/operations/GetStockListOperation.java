package io.elapse.stocks.operations;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import io.elapse.stocks.application.StocksApi;
import io.elapse.stocks.application.StocksContentProvider;
import io.elapse.stocks.models.Stock;
import io.pivotal.arca.dispatcher.ErrorBroadcaster;
import io.pivotal.arca.provider.DataUtils;
import io.pivotal.arca.service.ServiceError;
import io.pivotal.arca.service.SimpleOperation;

public class GetStockListOperation extends SimpleOperation {

    private String mSymbols;

    public GetStockListOperation(final String symbols) {
        super(StocksContentProvider.Uris.STOCKS);
        mSymbols = symbols;
    }

    public GetStockListOperation(final Parcel in) {
        super(in);
        mSymbols = in.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mSymbols);
    }

    @Override
    public ContentValues[] onExecute(final Context context) throws Exception {
        final Stock.List response = StocksApi.getStockList(mSymbols);
        return DataUtils.getContentValues(response);
    }

    @Override
    public void onComplete(final Context context, final Results results) {
        if (results.hasFailedTasks()) {
            final ServiceError error = results.getFailedTasks().get(0).getError();
            ErrorBroadcaster.broadcast(context, getUri(), error.getCode(), error.getMessage());
        }
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public GetStockListOperation createFromParcel(final Parcel in) {
            return new GetStockListOperation(in);
        }

        @Override
        public GetStockListOperation[] newArray(final int size) {
            return new GetStockListOperation[size];
        }
    };
}