package team1.fpoly.duan_n1_17303.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import team1.fpoly.duan_n1_17303.Dao.ProductRoomDAO;
import team1.fpoly.duan_n1_17303.Dao.RoomDao;
import team1.fpoly.duan_n1_17303.Object.ProductRoom;
import team1.fpoly.duan_n1_17303.Object.Room;
import team1.fpoly.duan_n1_17303.R;

public class RoomAdapter extends ArrayAdapter<Room> {
    Context context;
    ArrayList<Room> listRoom;
    RoomDao roomDao;

    TextView tv_Room_Name, tv_Location, tv_Type, tv_View, tv_Hour_Price, tv_Day_Price, tv_Note;
    ImageView iv_DeleteRoom, iv_Room_Product;

    public RoomAdapter(Context context, ArrayList<Room> listRoom, RoomDao roomDao) {
        super(context, 0, listRoom);
        this.context = context;
        this.listRoom = listRoom;
        this.roomDao = roomDao;
    }

    @Override
    public int getCount() {
        if (!listRoom.isEmpty()) return listRoom.size();
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        }
        final Room room = listRoom.get(position);
        if (room != null) {


            tv_Room_Name = convertView.findViewById(R.id.tv_Room_Name);
            tv_Location = convertView.findViewById(R.id.tv_Location);
            tv_Type = convertView.findViewById(R.id.tv_Type);
            tv_View = convertView.findViewById(R.id.tv_View);
            tv_Hour_Price = convertView.findViewById(R.id.tv_Hour_Price);
            tv_Day_Price = convertView.findViewById(R.id.tv_Day_Price);
          //  tv_Note = convertView.findViewById(R.id.tv_Note);
            iv_DeleteRoom = convertView.findViewById(R.id.iv_DeleteRoom);
            iv_Room_Product = convertView.findViewById(R.id.iv_Room_Product);
//            btn_Add_Note_Room = convertView.findViewById(R.id.btn_Add_Note_Room);

            tv_Room_Name.setText("Tên phòng: " + room.getRoomName());
            tv_Location.setText("Vị trí phòng: " + room.getRoomLocation());
            tv_Type.setText("Loại phòng: " + room.getRoomType());
            tv_View.setText("View phòng: " + room.getRoomView());
            tv_Hour_Price.setText("Giá giờ: " + room.getHourPrice());
            tv_Day_Price.setText("Giá ngày: " + room.getDayPrice());
        //       if (room.getNote() != null) {
        //         tv_Note.setVisibility(View.VISIBLE);
        //         tv_Note.setText("Ghi chú: " + room.getNote());
        //        btn_Add_Note_Room.setText("Sửa ghi chú");
        //    }

            iv_DeleteRoom.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa.");
                builder.setMessage("Phòng " + room.getRoomName());
                String room_id=String.valueOf(room.getRoomId());
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int check = roomDao.deleteRoom(room_id);
                        switch (check) {
                            case 1:
                                Toast.makeText(context.getApplicationContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                                listRoom.remove(room);
                                notifyDataSetChanged();
                                break;
                            case -1:
                                Toast.makeText(context.getApplicationContext(), "Phòng đã tồn tại trong hóa đơn. Không thể xóa", Toast.LENGTH_LONG).show();
                                break;
                            case 0:
                                Toast.makeText(context.getApplicationContext(), "Xóa thất bại", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            });

        //    btn_Add_Note_Room.setOnClickListener(view -> {
        //        showInputNote(position);
        //    });

            iv_Room_Product.setOnClickListener(v -> {
                showDialogProductRoom(room.getRoomId());
            });

        }

        return convertView;
    }

    private void showInputNote(int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update_note);
        TextView tv_Title_Room = dialog.findViewById(R.id.tv_Title_Room);
        EditText ed_Note = dialog.findViewById(R.id.ed_Note);
        ImageView img_Back_Room = dialog.findViewById(R.id.img_Back_Room);
        ImageView img_Save_Room = dialog.findViewById(R.id.img_Save_Room);

        if (listRoom.get(position).getNote() != null) {
            tv_Title_Room.setText("Sửa ghi chú");
        }
        ed_Note.setText(listRoom.get(position).getNote());

        img_Back_Room.setOnClickListener(view -> {
            dialog.dismiss();
        });

        img_Save_Room.setOnClickListener(view -> {
            Room room = listRoom.get(position);

            room.setNote(ed_Note.getText().toString());

            roomDao.updateRoom(room);
            notifyDataSetChanged();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void showDialogProductRoom(int room_id){
        ProductRoomDAO productRoomDAO = new ProductRoomDAO(getContext());
        ArrayList<ProductRoom> aListPrR= productRoomDAO.getDataProductRoomByID(room_id);

        Dialog dialog = new Dialog(getContext());
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.setContentView(R.layout.dialog_add_product);
        Button btn_done = dialog.findViewById(R.id.dialog_add_product_button_save);
        ListView lv_Dialog = dialog.findViewById(R.id.dialog_add_product_listview);
        DialogAddProductRoomAdapter adapter = new DialogAddProductRoomAdapter(getContext(),aListPrR,room_id);

        lv_Dialog.setAdapter(adapter);
        dialog.show();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void setData(ArrayList<Room> listRoom) {
        this.listRoom = listRoom;
        notifyDataSetChanged();
    }
}
