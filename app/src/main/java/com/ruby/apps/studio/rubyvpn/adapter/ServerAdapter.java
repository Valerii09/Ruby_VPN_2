package com.ruby.apps.studio.rubyvpn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ruby.apps.studio.rubyvpn.R;
import com.ruby.apps.studio.rubyvpn.model.Server;
import com.ruby.apps.studio.rubyvpn.utils.OvpnUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Этот класс представляет адаптер для RecyclerView, который отображает список серверов.
 * Адаптер заполняет представление каждого элемента списка данными из объекта Server, который содержит информацию о сервере.
 * Адаптер реализует методы onCreateViewHolder(), onBindViewHolder() и getItemCount(),
 * которые являются обязательными для адаптеров RecyclerView.
 * onCreateViewHolder() создает новый ViewHolder для элемента списка,
 * onBindViewHolder() устанавливает данные элемента списка, а getItemCount() возвращает количество элементов в списке.
 * Класс также содержит вложенный класс ViewHolder, который представляет элемент списка и содержит ссылки на его представления.
 * ViewHolder также имеет метод bind(), который устанавливает данные элемента списка.
 * ServerAdapter использует интерфейс ServerClickCallback для обратного вызова при нажатии на элемент списка.
 * Интерфейс имеет только один метод onItemClick(), который вызывается при нажатии на элемент списка.
 */
public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ViewHolder> {


    private List<Server> servers = new ArrayList<>();

    private ServerClickCallback callback;

    public ServerAdapter(List<Server> servers, @NonNull ServerClickCallback callback) {
        this.servers.clear();
        this.servers.addAll(servers);
        this.callback = callback;
    }

    /**
     * @param serverList Эта функция устанавливает новый список серверов в адаптере и обновляет его.
     *                   Если текущий список серверов пуст, то новый список добавляется полностью,
     *                   иначе используется класс DiffUtil для вычисления разницы между старым и новым списком,
     *                   чтобы минимизировать количество необходимых изменений в адаптере. Затем происходит очистка текущего списка, д
     *                   обавление нового списка и вызов метода dispatchUpdatesTo(), чтобы передать обновления в адаптер. Функция также проверяет,
     *                   идентичны ли элементы в старом и новом списке по некоторым критериям, таким как hostname, ipAddress и countryLong,
     *                   чтобы определить, какие элементы нужно обновить.
     */

    public void setServerList(@NonNull final List<Server> serverList) {
        if (servers.isEmpty()) {
            servers.clear();
            servers.addAll(serverList);
            notifyItemRangeInserted(0, serverList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return servers.size();
                }

                @Override
                public int getNewListSize() {
                    return serverList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Server old = servers.get(oldItemPosition);
                    Server server = serverList.get(newItemPosition);
                    return old.hostName.equals(server.hostName);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Server old = servers.get(oldItemPosition);
                    Server server = serverList.get(newItemPosition);
                    return old.hostName.equals(server.hostName)
                            && old.ipAddress.equals(server.ipAddress)
                            && old.countryLong.equals(server.countryLong);
                }
            });
            servers.clear();
            servers.addAll(serverList);
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.server_list_item, parent, false);
        return new ViewHolder(view, callback);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(servers.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return servers == null ? 0 : servers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final View rootView;
        final TextView countryView;
        final TextView protocolView;
        final TextView ipAddressView;
        final TextView speedView;
        final TextView pingView;

        final ServerClickCallback callback;

        /**
         * @param view Эта функция создает ViewHolder, который содержит элементы пользовательского интерфейса (TextViews)
         *             для отображения информации о серверах. Она также устанавливает ServerClickCallback для обработки
         *             события щелчка на элементе списка серверов. Когда создается новый ViewHolder,
         *             он связывается со своим макетом, который содержит TextViews для отображения информации о сервере.
         *             Эти TextViews затем сохраняются в переменных класса ViewHolder.
         *             Кроме того, функция сохраняет переданный ServerClickCallback
         *             для дальнейшего использования в обработке событий щелчка.
         */
        public ViewHolder(View view, ServerClickCallback callback) {
            super(view);
            rootView = view;
            countryView = view.findViewById(R.id.tv_country_name);
            protocolView = view.findViewById(R.id.tv_protocol);
            ipAddressView = view.findViewById(R.id.tv_ip_address);
            speedView = view.findViewById(R.id.tv_speed);
            pingView = view.findViewById(R.id.tv_ping);

            this.callback = callback;
        }

        public void bind(@NonNull final Server server) {
            final Context context = rootView.getContext();

            countryView.setText(server.countryLong);
            protocolView.setText(server.protocol.toUpperCase());
            ipAddressView.setText(context.getString(R.string.format_ip_address,
                    server.ipAddress, server.port));
            speedView.setText(context.getString(R.string.format_speed,
                    OvpnUtils.humanReadableCount(server.speed, true)));
            pingView.setText(context.getString(R.string.format_ping, server.ping));
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onItemClick(server);
                }
            });
        }
    }

    public interface ServerClickCallback {
        void onItemClick(@NonNull Server server);
    }
}
