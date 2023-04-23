package com.apiguave.tinderclonecompose.ui.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.koin.androidx.compose.getViewModel
import androidx.navigation.*
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.ui.*
import com.apiguave.tinderclonecompose.ui.chat.ChatView
import com.apiguave.tinderclonecompose.ui.chat.ChatViewModel
import com.apiguave.tinderclonecompose.ui.components.AddPictureView
import com.apiguave.tinderclonecompose.ui.editprofile.EditProfileView
import com.apiguave.tinderclonecompose.ui.editprofile.EditProfileViewModel
import com.apiguave.tinderclonecompose.ui.home.HomeView
import com.apiguave.tinderclonecompose.ui.home.HomeViewModel
import com.apiguave.tinderclonecompose.ui.login.LoginView
import com.apiguave.tinderclonecompose.ui.login.LoginViewModel
import com.apiguave.tinderclonecompose.ui.matchlist.MatchListView
import com.apiguave.tinderclonecompose.ui.matchlist.MatchListViewModel
import com.apiguave.tinderclonecompose.ui.newmatch.NewMatchView
import com.apiguave.tinderclonecompose.ui.newmatch.NewMatchViewModel
import com.apiguave.tinderclonecompose.ui.signup.SignUpView
import com.apiguave.tinderclonecompose.ui.signup.SignUpViewModel
import com.apiguave.tinderclonecompose.ui.theme.TinderCloneComposeTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent(signInClient: GoogleSignInClient){
    TinderCloneComposeTheme {
        val navController = rememberAnimatedNavController()

        val chatViewModel: ChatViewModel = getViewModel()
        val newMatchViewModel: NewMatchViewModel = getViewModel()
        val editProfileViewModel: EditProfileViewModel = getViewModel()
        val signUpViewModel: SignUpViewModel = getViewModel()
        AnimatedNavHost(navController = navController, startDestination = Routes.Login) {

            animatedComposable(Routes.Login) {
                val loginViewModel: LoginViewModel = koinViewModel()
                val uiState by loginViewModel.uiState.collectAsState()

                LoginView(
                    signInClient = signInClient,
                    uiState = uiState,
                    onNavigateToSignUp = {
                        navController.navigate(Routes.SignUp)
                    },
                    onNavigateToHome = {
                        navController.navigate(Routes.Home){
                            popUpTo(Routes.Login){
                                inclusive = true
                            }
                        }
                    },
                    onSignIn = loginViewModel::signIn

                )
            }

            animatedComposable(Routes.SignUp) {
                val uiState by signUpViewModel.uiState.collectAsState()
                SignUpView(
                    uiState = uiState,
                    signInClient = signInClient,
                    onAddPicture = {
                        navController.navigate(Routes.getAddPictureRoute(Routes.SignUp))
                    },
                    onNavigateToHome = {
                        navController.navigate(Routes.Home){
                            popUpTo(Routes.SignUp){
                                inclusive = true
                            }
                        }
                    },
                    removePictureAt = signUpViewModel::removePictureAt,
                    signUp = signUpViewModel::signUp
                )
            }

            animatedComposable(Routes.AddPicture, arguments = listOf(navArgument(Arguments.Caller){ type = NavType.StringType})){
                AddPictureView(
                    onCloseClicked = navController::popBackStack
                    ,
                    onReceiveUri = { uri, caller ->
                        if(caller == Routes.SignUp){
                            signUpViewModel.addPicture(uri)
                        }else if(caller == Routes.EditProfile){
                            editProfileViewModel.addPicture(uri)
                        }
                        navController.popBackStack()
                    },
                    caller = it.arguments?.getString(Arguments.Caller)
                )
            }

            animatedComposable(Routes.Home, animationType = AnimationType.HOME) {
                val homeViewModel: HomeViewModel = koinViewModel()
                val uiState by homeViewModel.uiState.collectAsState()
                HomeView(
                    uiState = uiState,
                    navigateToEditProfile = {
                        navController.navigate(Routes.EditProfile)
                    },
                    navigateToMatchList = {
                        navController.navigate(Routes.MatchList)
                    },
                    navigateToNewMatch = {
                        navController.navigate(Routes.NewMatch)
                    },
                    setLoading = homeViewModel::setLoading,
                    removeLastProfile = homeViewModel::removeLastProfile,
                    fetchProfiles = homeViewModel::fetchProfiles,
                    swipeUser = homeViewModel::swipeUser,
                    createProfiles = homeViewModel::createProfiles,
                    setMatch = newMatchViewModel::setMatch,
                    setCurrentProfile = editProfileViewModel::setCurrentProfile,
                    newMatch = homeViewModel.newMatch,
                    currentProfile = homeViewModel.currentProfile
                )
            }

            animatedComposable(Routes.NewMatch, animationType = AnimationType.FADE){
                val newMatch by newMatchViewModel.match.collectAsState()
                NewMatchView(
                    match = newMatch,
                    onSendMessage = {
                        newMatchViewModel.sendMessage(it)
                        navController.popBackStack()
                    },
                    onCloseClicked = navController::popBackStack
                )
            }

            animatedComposable(Routes.EditProfile){
                val uiState by editProfileViewModel.uiState.collectAsState()
                EditProfileView(
                    uiState = uiState,
                    addPicture = {
                        navController.navigate(Routes.getAddPictureRoute(Routes.EditProfile))
                    },
                    onProfileEdited = navController::popBackStack
                    ,
                    onSignedOut = {
                        navController.navigate(Routes.Login){
                            popUpTo(Routes.Home){
                                inclusive = true
                            }
                        }
                    },
                    removePictureAt = editProfileViewModel::removePictureAt,
                    signOut = {editProfileViewModel.signOut(signInClient)},
                    updateProfile = editProfileViewModel::updateProfile,
                    action = editProfileViewModel.action
                )
            }

            animatedComposable(Routes.MatchList){
                val viewModel: MatchListViewModel = koinViewModel()
                val uiState by viewModel.uiState.collectAsState()
                MatchListView(
                    uiState = uiState,
                    navigateToMatch = {
                        chatViewModel.setMatch(it)
                        navController.navigate(Routes.Chat)
                    },
                    onArrowBackPressed =  navController::popBackStack,
                    fetchMatches = viewModel::fetchMatches
                )
            }

            animatedComposable(Routes.Chat){
                val viewModel: ChatViewModel = koinViewModel()
                val chatMatch by viewModel.match.collectAsState()
                chatMatch?.let {
                    val messages by viewModel.getMessages(it.id).collectAsState(initial = listOf())
                    ChatView(
                        match = it,
                        messages = messages,
                        onArrowBackPressed = navController::popBackStack,
                        sendMessage = viewModel::sendMessage,
                    )
                }  ?: run{
                    Text(modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center, text = stringResource(id = R.string.no_match_value_passed))
                }
            }
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.animatedComposable(
    route: String,
    animationType: AnimationType = AnimationType.SLIDE,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(route, arguments, deepLinks,
        enterTransition = animationType.enterTransition,
        exitTransition = animationType.exitTransition,
        popEnterTransition = animationType.popEnterTransition,
        popExitTransition = animationType.popExitTransition,
        content = content
    )
}