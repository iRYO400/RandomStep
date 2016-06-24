package com.r400.ultra.free.rwallpapers.fragments.genres;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.r400.ultra.free.rwallpapers.R;
import com.r400.ultra.free.rwallpapers.Utilities.TinyDB;
import com.r400.ultra.free.rwallpapers.model.MyGenre;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Whitesteel on 29.02.2016.
 */
public class CGAdapter extends RecyclerView.Adapter<CGAdapter.ViewHolder> {

    ArrayList<MyGenre> genreList;
    private Context mContext;

    private TinyDB tinyDB;
    private View view;

    private ArrayList<MyGenre> cgMyGenreArrayList;
    private ArrayList<MyGenre> moviesMyGenreArrayList;
    private ArrayList<MyGenre> worldMyGenreArrayList;
    private ArrayList<MyGenre> craftsMyGenreArrayList;

    static final int TYPE_CELL = 2;

    public CGAdapter(Context context, ArrayList<MyGenre> contents) {
        this.mContext = context;
        this.genreList = contents;
    }

    public interface AdapterCallback{
        public void updateChecked(CGAdapter cgAdapter);
    }

    private AdapterCallback adapterCallbackListener;


    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox chkSelected;

        private TextView name;
        private TextView info;

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);


            Typeface CF = Typeface.createFromAsset(mContext.getAssets(), mContext.getString(R.string.title_font));
            name = (TextView) itemView.findViewById(R.id.tvName);
            ((TextView) itemView.findViewById(R.id.tvName)).setTypeface(CF);
            Typeface CF2 = Typeface.createFromAsset(mContext.getAssets(), mContext.getString(R.string.inside_font));
            info = (TextView) itemView.findViewById(R.id.descriptionText);
            ((TextView) itemView.findViewById(R.id.descriptionText)).setTypeface(CF2);

            imageView = (ImageView) itemView.findViewById(R.id.logoContainer);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
        }
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }


    //Создает контейнеры, где будут находиться весь стафф
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = null;

        switch (viewType) {
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new ViewHolder(view) {
                };
            }
        }
        return null;
    }


    //Что-то вроде обработчика
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final int pos = position;
        adapterCallbackListener = (AdapterCallback) mContext;
        tinyDB = new TinyDB(view.getContext());

        cgMyGenreArrayList = new ArrayList<>();
        moviesMyGenreArrayList = new ArrayList<>();
        worldMyGenreArrayList = new ArrayList<>();
        craftsMyGenreArrayList = new ArrayList<>();


        viewHolder.name.setText(genreList.get(position).getName());
        viewHolder.info.setText(genreList.get(position).getInfo());
        Picasso.with(mContext)
                .load(genreList.get(position).getImage())
                .noPlaceholder()
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .networkPolicy(NetworkPolicy.NO_STORE)
                .into(viewHolder.imageView);

        //Восстанавливаем Чекбоксы из предыдущей сессии
        switch (genreList.get(0).getName()) {
            case "3D and Abstract":
                cgMyGenreArrayList = tinyDB.getListGenres("listFromCGAdapter", MyGenre.class);
                if (!cgMyGenreArrayList.isEmpty()) {
                    viewHolder.chkSelected.setChecked(cgMyGenreArrayList.get(position).isSelected());
                    genreList.get(pos).setSelected(viewHolder.chkSelected.isChecked());
                }
                break;
            case "Cartoons":
                moviesMyGenreArrayList = tinyDB.getListGenres("listFromMoviesAdapter", MyGenre.class);
                if (!moviesMyGenreArrayList.isEmpty()) {
                    viewHolder.chkSelected.setChecked(moviesMyGenreArrayList.get(position).isSelected());
                    genreList.get(pos).setSelected(viewHolder.chkSelected.isChecked());
                }
                break;
            case "Animals":
                worldMyGenreArrayList = tinyDB.getListGenres("listFromWorldAdapter", MyGenre.class);
                if (!worldMyGenreArrayList.isEmpty()) {
                    viewHolder.chkSelected.setChecked(worldMyGenreArrayList.get(position).isSelected());
                    genreList.get(pos).setSelected(viewHolder.chkSelected.isChecked());
                }
                break;
            case "Aircraft":
                craftsMyGenreArrayList = tinyDB.getListGenres("listFromCraftsAdapter", MyGenre.class);
                if (!craftsMyGenreArrayList.isEmpty()) {
                    viewHolder.chkSelected.setChecked(craftsMyGenreArrayList.get(position).isSelected());
                    genreList.get(pos).setSelected(viewHolder.chkSelected.isChecked());
                }
                break;
        }


        //Сохраняем Чекбоксы и Чекнутые жанры в tinyDB
        viewHolder.chkSelected.setTag(genreList.get(position));
        viewHolder.chkSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckBox checkBox = (CheckBox) buttonView;
                MyGenre gr = (MyGenre) checkBox.getTag();
                tinyDB = new TinyDB(buttonView.getContext());

                gr.setSelected(checkBox.isChecked());
                genreList.get(pos).setSelected(checkBox.isChecked());

                switch (genreList.get(0).getName()) {
                    case "3D and Abstract":
                        tinyDB.putListGenres("listFromCGAdapter", genreList);
                        break;
                    case "Cartoons":
                        tinyDB.putListGenres("listFromMoviesAdapter", genreList);
                        break;
                    case "Animals":
                        tinyDB.putListGenres("listFromWorldAdapter", genreList);
                        break;
                    case "Aircraft":
                        tinyDB.putListGenres("listFromCraftsAdapter", genreList);
                        break;
                }
                adapterCallbackListener.updateChecked(CGAdapter.this);
            }
        });
    }
}

