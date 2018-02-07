package app.english.grammar.craftystudio.englishgrammar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import utils.Questions;

/**
 * Created by Aisha on 2/6/2018.
 */

public class AptitudeFragment extends Fragment implements View.OnClickListener {

    ProgressDialog progressDialog;
    static Context DailyQuestionActivity;

    private OnFragmentInteractionListener mListener;

    Questions questions;

    TextView optionA;
    TextView optionB;
    TextView optionC;
    TextView optionD;

    CardView optionACardView;
    CardView optionBCardView;
    CardView optionCCardView;
    CardView optionDCardView;

    public static AptitudeFragment newInstance(Questions questions, Context context) {

        DailyQuestionActivity = context;
        AptitudeFragment fragment = new AptitudeFragment();
        Bundle args = new Bundle();
        args.putSerializable("Question", questions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.questions = (Questions) getArguments().getSerializable("Question");

           /* Answers.getInstance().logContentView(new ContentViewEvent()
                    .putContentName(story.getStoryTitle())
                    .putContentId(story.getStoryID())
            );
            */

        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily_quiz, container, false);
        //initializeView

        TextView questionName = (TextView) view.findViewById(R.id.fragmentAptitudeQuiz_QuestionName_Textview);
        optionA = (TextView) view.findViewById(R.id.fragmentAptitudeQuiz_optionA_Textview);
        optionB = (TextView) view.findViewById(R.id.fragmentAptitudeQuiz_optionB_Textview);
        optionC = (TextView) view.findViewById(R.id.fragmentAptitudeQuiz_optionC_Textview);
        optionD = (TextView) view.findViewById(R.id.fragmentAptitudeQuiz_optionD_Textview);

        optionACardView = (CardView) view.findViewById(R.id.fragmentAptitudeQuiz_optionA_Cardview);
        optionBCardView = (CardView) view.findViewById(R.id.fragmentAptitudeQuiz_optionB_Cardview);
        optionCCardView = (CardView) view.findViewById(R.id.fragmentAptitudeQuiz_optionC_Cardview);
        optionDCardView = (CardView) view.findViewById(R.id.fragmentAptitudeQuiz_optionD_Cardview);


        questionName.setText("Q. " + questions.getQuestionName());
        optionA.setText(questions.getOptionA());
        optionB.setText(questions.getOptionB());
        optionC.setText(questions.getOptionC());
        optionD.setText(questions.getOptionD());


        optionACardView.setOnClickListener(this);
        optionBCardView.setOnClickListener(this);
        optionCCardView.setOnClickListener(this);
        optionDCardView.setOnClickListener(this);


       // getUserAnswers();

        //initializeNativeAd(view);


        return view;
    }

    private void getUserAnswers() {


        if (questions.getUserAnswer() != null) {


            if (questions.getUserAnswer().equalsIgnoreCase(questions.getCorrectAnswer())) {
                if (questions.getOptionA().trim().equalsIgnoreCase(questions.getUserAnswer().trim())) {
                    optionACardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

                } else if (questions.getOptionB().trim().equalsIgnoreCase(questions.getUserAnswer().trim())) {
                    optionBCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));


                } else if (questions.getOptionC().trim().equalsIgnoreCase(questions.getUserAnswer().trim())) {
                    optionCCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

                } else if (questions.getOptionD().trim().equalsIgnoreCase(questions.getUserAnswer().trim())) {
                    optionDCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

                }

            } else {
                if (questions.getOptionA().trim().equalsIgnoreCase(questions.getUserAnswer().trim())) {
                    optionACardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));

                } else if (questions.getOptionB().trim().equalsIgnoreCase(questions.getUserAnswer().trim())) {
                    optionBCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));


                } else if (questions.getOptionC().trim().equalsIgnoreCase(questions.getUserAnswer().trim())) {
                    optionCCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));

                } else if (questions.getOptionD().trim().equalsIgnoreCase(questions.getUserAnswer().trim())) {
                    optionDCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));

                }


            }

        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void getRightAnswer() {
        String correctANswer = questions.getCorrectAnswer().trim();

        if (questions.getOptionA().trim().equalsIgnoreCase(correctANswer)) {
            optionACardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

        } else if (questions.getOptionB().trim().equalsIgnoreCase(correctANswer)) {
            optionBCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

        } else if (questions.getOptionC().trim().equalsIgnoreCase(correctANswer)) {
            optionCCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

        } else if (questions.getOptionD().trim().equalsIgnoreCase(correctANswer)) {
            optionDCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
        }
    }


    @Override
    public void onClick(View view) {

        //normal topic questions and normal test series

        switch (view.getId()) {

            case R.id.fragmentAptitudeQuiz_optionA_Cardview:
                if (questions.getOptionA().equalsIgnoreCase(questions.getCorrectAnswer())) {
                    Toast.makeText(DailyQuestionActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionACardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));


                } else {
                    // Toast.makeText(mainActivity, "Wrong Answer", Toast.LENGTH_SHORT).show();
                    optionACardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    getRightAnswer();
                }
                break;

            case R.id.fragmentAptitudeQuiz_optionB_Cardview:
                if (questions.getOptionB().equalsIgnoreCase(questions.getCorrectAnswer())) {
                    Toast.makeText(DailyQuestionActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionBCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

                } else {
                    // Toast.makeText(mainActivity, "Wrong Answer", Toast.LENGTH_SHORT).show();
                    optionBCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    getRightAnswer();
                }
                break;
            case R.id.fragmentAptitudeQuiz_optionC_Cardview:
                if (questions.getOptionC().equalsIgnoreCase(questions.getCorrectAnswer())) {
                    Toast.makeText(DailyQuestionActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionCCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

                } else {
                    //  Toast.makeText(mainActivity, "Wrong Answer", Toast.LENGTH_SHORT).show();
                    optionCCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    getRightAnswer();
                }
                break;
            case R.id.fragmentAptitudeQuiz_optionD_Cardview:
                if (questions.getOptionD().equalsIgnoreCase(questions.getCorrectAnswer())) {
                    Toast.makeText(DailyQuestionActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionDCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));

                } else {
                    // Toast.makeText(mainActivity, "Wrong Answer", Toast.LENGTH_SHORT).show();
                    optionDCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    getRightAnswer();
                }
                break;

        }


        try {
            //  Answers.getInstance().logContentView(new ContentViewEvent().putContentId("question answer"));
        } catch (Exception e)

        {
            e.printStackTrace();
        }


    }

}
