        package com.example.nb;


        import android.content.Intent;
        import android.content.Context;

        import android.content.SharedPreferences;
        import android.os.Bundle;


        import androidx.annotation.Nullable;
        import androidx.appcompat.widget.AppCompatButton;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentTransaction;

        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.google.android.gms.auth.api.signin.GoogleSignIn;
        import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
        import com.google.android.gms.auth.api.signin.GoogleSignInClient;
        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
        import com.google.android.gms.common.api.ApiException;

        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;



        public class LoginFragment extends Fragment {
            AppCompatButton loginGoogleButton;
            private static final String TAG = "LoginFragment";
            private static final int RC_SIGN_IN = 9001;

            private GoogleSignInClient mGoogleSignInClient;
            private FirebaseAuth mAuth;
            private SharedPreferences sharedPreferences;


            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                View view= inflater.inflate(R.layout.fragment_login, container, false);
                loginGoogleButton=view.findViewById(R.id.loginGoogleButton);

                mAuth = FirebaseAuth.getInstance();
                sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

                loginGoogleButton = view.findViewById(R.id.loginGoogleButton);
                loginGoogleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signIn();
                    }
                });

                return view;
            }
            private void signIn() {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }

            @Override
            public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
                if (requestCode == RC_SIGN_IN) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        // Google Sign In was successful, authenticate with Firebase
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        firebaseAuthWithGoogle(account);
                    } catch (ApiException e) {
                        // Google Sign In failed
                        Log.w(TAG, "Google sign in failed", e);
                    }
                }
            }

            private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

                // Save email to SharedPreferences
                String email = account.getEmail();
                saveEmailToSharedPreferences(email);

                // Move to TextFragment upon successful authentication
                Fragment mainFragment = new MainFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, mainFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            private void saveEmailToSharedPreferences(String email) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.apply();
            }


        }