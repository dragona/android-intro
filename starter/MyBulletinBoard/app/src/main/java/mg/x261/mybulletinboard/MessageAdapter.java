package mg.x261.mybulletinboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messageList;
    private LayoutInflater layoutInflater;

    public MessageAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.news_item, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message currentMessage = messageList.get(position);
        holder.bind(currentMessage);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNewsTitle;
        TextView textViewNewsContent;
        TextView textViewTimestamp;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNewsTitle = itemView.findViewById(R.id.tv_news_title);
            textViewNewsContent = itemView.findViewById(R.id.tv_news_content);
            textViewTimestamp = itemView.findViewById(R.id.tv_timestamp);
        }


        void bind(Message message) {
            String deviceId = message.getDeviceId();
            if (deviceId.length() > 4) {
                String shortenedDeviceId = deviceId.substring(0,2) + "###" + deviceId.substring(deviceId.length() - 3);
                textViewNewsTitle.setText(shortenedDeviceId);
            } else {
                textViewNewsTitle.setText(deviceId);
            }

            textViewNewsContent.setText(message.getMessage());

            long timestamp = Long.parseLong(message.getTimerServer()) * 1000L; // Assuming the timer server value is in seconds
            Date date = new Date(timestamp);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = dateFormat.format(date);

            Date currentDate = new Date();
            String formattedCurrentDate = dateFormat.format(currentDate);

            if (formattedDate.equals(formattedCurrentDate)) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String formattedTime = timeFormat.format(date);
                textViewTimestamp.setText("Today, " + formattedTime);
            } else {
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String formattedDateTime = dateTimeFormat.format(date);
                textViewTimestamp.setText(formattedDateTime);
            }

        }
    }
}
