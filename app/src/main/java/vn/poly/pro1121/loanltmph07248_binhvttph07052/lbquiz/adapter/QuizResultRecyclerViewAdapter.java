package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.FunctionLibrary;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.R;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.QuizResultDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.dao.SaveResultDao;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.model.QuizResult;
import vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.show_save_results.model.entity.SaveResult;

import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.KEY_USERNAME;
import static vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.PublicVariableLibrary.SHARED_PREFERENCES_NAME;

public class QuizResultRecyclerViewAdapter extends RecyclerView.Adapter<QuizResultRecyclerViewAdapter.QuizResultViewHolder>
    implements Filterable {
    Activity context;
    List<SaveResult> saveResultList;
    List<SaveResult> resultListFull;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm a");

    //database
    QuizResultDao quizResultDao;
    SaveResultDao saveResultDao;

    //library
    FunctionLibrary functionLibrary;

    //debug
    private static final String TAG = "resultAdapterLog";

    public QuizResultRecyclerViewAdapter(Activity context, List<SaveResult> saveResultList) {
        this.context = context;
        this.saveResultList = saveResultList;
        quizResultDao = new QuizResultDao(context);
        functionLibrary = new FunctionLibrary();
        saveResultDao = new SaveResultDao(context);

        //filter
        resultListFull = new ArrayList<>(saveResultList);
    }

    @NonNull
    @Override
    public QuizResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_quiz_result, parent, false);
        QuizResultViewHolder quizResultViewHolder = new QuizResultViewHolder(view);
        return quizResultViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizResultViewHolder holder, int position) {
        final int pos = position;

        SaveResult saveResult = saveResultList.get(position);
        holder.tvQuizTitle.setText(saveResult.getQuiz().getName());
        holder.tvQuizScore.setText(
                context.getResources().getString(R.string.score)+
                        " "+saveResult.getScore());

        String resultInText = getResultInText(saveResult);
        holder.tvResultTitle.setText(
                context.getResources().getString(R.string.result_title)+
                        " "+resultInText);

        holder.tvResultSaveTime.setText(sdf.format(saveResult.getSaveTime()));

        holder.imageButtonDeleteResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeleteDialog(pos);
            }
        });
    }

    /**
     * truy van lay ket qua, xu ly ket qua
     * @param saveResult
     */
    private String getResultInText(SaveResult saveResult) {
        int quizId = saveResult.getQuiz().getId();
        double score = saveResult.getScore();
        QuizResult quizResultForThisScore =
                quizResultDao.getResultByQuizIdAndScore(quizId, score);
        String resultTitle = quizResultForThisScore.getTitle();
        String resultDescription = quizResultForThisScore.getDescription();
        String resultText;
        if (!resultTitle.trim().isEmpty()) {
            resultText = resultTitle;
        } else {
            //title empty
            if (functionLibrary.countWords(resultDescription) < 15) {
                resultText = resultDescription;
            } else {
                resultText =
                        functionLibrary.getFirstXWordsOfString(
                                resultDescription, 15
                        )
                        +"...";
            }
        }

        return resultText;
    }

    private void callDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.do_you_want_to_delete_this_result);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteResult(position);
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteResult(int position) {
        SaveResult saveResult = saveResultList.get(position);

        int result = saveResultDao.deleteSaveResult(saveResult);
        if (result > 0) {
            Toast.makeText(
                    context,
                    R.string.delete_successfully,
                    Toast.LENGTH_SHORT
            ).show();
            refreshAdapter();
        } else {
            Toast.makeText(
                    context,
                    R.string.delete_failed,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void refreshAdapter() {
        String username = getUsernameLoggedIn();
        saveResultList.clear();
        saveResultList.addAll(saveResultDao.getAllSaveResultsOrderBySaveTimeNewestToOldest(username));
        notifyDataSetChanged();
    }

    private String getUsernameLoggedIn() {
        String username;
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(
                        SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                );
        //khoi phuc lai gia tri
        username = sharedPreferences.getString(KEY_USERNAME, "");
        return username;
    }

    @Override
    public int getItemCount() {
        return saveResultList.size();
    }

    @Override
    public Filter getFilter() {
        return saveResultFilter;
    }

    private Filter saveResultFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SaveResult> filteredList = new ArrayList<>();//list perform filter

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(resultListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase();

                for (SaveResult saveResultItem : resultListFull) {
                    if (saveResultItem.getQuiz().getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(saveResultItem);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            saveResultList.clear();
            saveResultList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class QuizResultViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuizTitle;
        private TextView tvQuizScore;
        private TextView tvResultTitle;
        private TextView tvResultSaveTime;
        private ImageButton imageButtonDeleteResult;


        public QuizResultViewHolder(@NonNull View itemView) {
            super(itemView);

            tvQuizTitle = (TextView) itemView.findViewById(R.id.tvQuizTitle);
            tvQuizScore = (TextView) itemView.findViewById(R.id.tvQuizScore);
            tvResultTitle = (TextView) itemView.findViewById(R.id.tvResultTitle);
            tvResultSaveTime = (TextView) itemView.findViewById(R.id.tvResultSaveTime);
            imageButtonDeleteResult = (ImageButton) itemView.findViewById(R.id.imageButtonDeleteResult);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveResult saveResult = saveResultList.get(getAdapterPosition());
                    QuizResult quizResult = getQuizResult(saveResult);
                    getResultSetToDialog(quizResult);
                }
            });
        }

        private void getResultSetToDialog(QuizResult quizResult) {
            String resultTitle = quizResult.getTitle();
            String resultDescription = quizResult.getDescription();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                LayoutInflater inflater = context.getLayoutInflater();
                View resultView = inflater.inflate(R.layout.dialog_quiz_result, null);
                TextView tvResultTitle = resultView.findViewById(R.id.tvResultTitle);
                TextView tvResultDescription = resultView.findViewById(R.id.tvResultDescription);
                tvResultTitle.setText(resultTitle);
                tvResultDescription.setText(resultDescription);

                builder.setView(resultView);
            } else {
                builder.setMessage(
                        String.format("%s \n%s", resultTitle, resultDescription));
            }

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        private QuizResult getQuizResult(SaveResult saveResult) {
            int quizId = saveResult.getQuiz().getId();
            double score = saveResult.getScore();
            QuizResult quizResultForThisScore =
                    quizResultDao.getResultByQuizIdAndScore(quizId, score);
            return quizResultForThisScore;
        }
    }
}
