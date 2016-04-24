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
import com.r400.ultra.free.rwallpapers.activity.Genres;

import java.util.ArrayList;


/**
 * Created by Whitesteel on 29.02.2016.
 */
public class CGAdapter extends RecyclerView.Adapter<CGAdapter.ViewHolder> {

    ArrayList<Genres> genreList;
    private Context mContext;

    private TinyDB tinyDB;
    private View view;

    private ArrayList<Genres> cgGenresArrayList;
    private ArrayList<Genres> moviesGenresArrayList;
    private ArrayList<Genres> worldGenresArrayList;
    private ArrayList<Genres> craftsGenresArrayList;

    static final int TYPE_CELL = 2;

    public CGAdapter(Context context, ArrayList<Genres> contents) {
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

        private TextView description;

        private TextView add2Pool;

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);


            Typeface CF = Typeface.createFromAsset(mContext.getAssets(), mContext.getString(R.string.title_font));
            name = (TextView) itemView.findViewById(R.id.tvName);
            ((TextView) itemView.findViewById(R.id.tvName)).setTypeface(CF);

            Typeface CF2 = Typeface.createFromAsset(mContext.getAssets(), mContext.getString(R.string.inside_font));
            ((TextView) itemView.findViewById(R.id.txtAdd)).setTypeface(CF2);

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
//            case TYPE_HEADER: {
//                view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_item_card_big, parent, false);
//                return new ViewHolder(view) {
//                };
//            }
//            case TYPE_HEADER2: {
//                view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_item_card_big, parent, false);
//                return new ViewHolder(view) {
//                };
//            }
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

        cgGenresArrayList = new ArrayList<>();
        moviesGenresArrayList = new ArrayList<>();
        worldGenresArrayList = new ArrayList<>();
        craftsGenresArrayList = new ArrayList<>();


        viewHolder.name.setText(genreList.get(position).getName());
        viewHolder.imageView.setImageResource(genreList.get(position).getImgRes());

        switch (genreList.get(0).getName()) {
            case "3D and Abstract":
                cgGenresArrayList = tinyDB.getListObject("listFromCGAdapter", Genres.class);
                if (!cgGenresArrayList.isEmpty()) {
                    viewHolder.chkSelected.setChecked(cgGenresArrayList.get(position).isSelected());
                    genreList.get(pos).setSelected(viewHolder.chkSelected.isChecked());
                }
                break;
            case "Cartoons":
                moviesGenresArrayList = tinyDB.getListObject("listFromMoviesAdapter", Genres.class);
                if (!moviesGenresArrayList.isEmpty()) {
                    viewHolder.chkSelected.setChecked(moviesGenresArrayList.get(position).isSelected());
                    genreList.get(pos).setSelected(viewHolder.chkSelected.isChecked());
                }
                break;
            case "Animals":
                worldGenresArrayList = tinyDB.getListObject("listFromWorldAdapter", Genres.class);
                if (!worldGenresArrayList.isEmpty()) {
                    viewHolder.chkSelected.setChecked(worldGenresArrayList.get(position).isSelected());
                    genreList.get(pos).setSelected(viewHolder.chkSelected.isChecked());
                }
                break;
            case "Aircraft":
                craftsGenresArrayList = tinyDB.getListObject("listFromCraftsAdapter", Genres.class);
                if (!craftsGenresArrayList.isEmpty()) {
                    viewHolder.chkSelected.setChecked(craftsGenresArrayList.get(position).isSelected());
                    genreList.get(pos).setSelected(viewHolder.chkSelected.isChecked());
                }
                break;
        }


        viewHolder.chkSelected.setTag(genreList.get(position));
        viewHolder.chkSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckBox checkBox = (CheckBox) buttonView;
                Genres gr = (Genres) checkBox.getTag();
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

