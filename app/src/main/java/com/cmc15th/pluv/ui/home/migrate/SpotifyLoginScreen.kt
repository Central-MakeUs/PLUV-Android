
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.cmc15th.pluv.BuildConfig
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

private const val TAG = "PlaylistLoginScreen"
@Composable
fun PlaylistLoginScreen(
    onLoginSuccess: (String) -> Unit = {},
    onLoginError: (String) -> Unit = {}
) {

    val context = LocalContext.current as Activity
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val response = AuthorizationClient.getResponse(result.resultCode, result.data)

        when (response.type) {
            AuthorizationResponse.Type.TOKEN -> {
                // 로그인 성공
                val accessToken = response.accessToken
                Log.d(TAG, "PlaylistLoginScreen:  $accessToken ")
                onLoginSuccess(accessToken)
            }
            AuthorizationResponse.Type.ERROR -> {
                // 로그인 실패
                val error = response.error
                Log.e(TAG, "PlaylistLoginScreen: $error")
                onLoginError(error)
            }
            else -> {
                // 기타 처리
                onLoginError("Unknown error occurred")
            }
        }
    }

    /**
     * Spotify 로그인을 위한 인증 요청 객체 생성 (비공개 플리까지 가져오게끔)
     */
    val spotifyBuilder = AuthorizationRequest.Builder(
        BuildConfig.spotify_client_id,
        AuthorizationResponse.Type.TOKEN,
        BuildConfig.spotify_redirect_uri
    ).apply {
        setScopes(arrayOf("user-read-private", "playlist-read"))
    }.build()

    val loginIntent = AuthorizationClient.createLoginActivityIntent(context, spotifyBuilder)

    LaunchedEffect(Unit) {
        launcher.launch(loginIntent)
    }
}
