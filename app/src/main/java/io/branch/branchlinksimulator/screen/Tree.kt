package io.branch.branchlinksimulator.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.branch.branchlinksimulator.R
import io.branch.branchlinksimulator.RoundedButton
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.util.LinkProperties

@Composable
fun Tree(
    title: String,
    deepLinkParams: Map<String, String>
) {
    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(16.dp))

            if (deepLinkParams.isEmpty()) {
                Text(
                    "Open Deep Link to View Parameters",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                Text("Deep Link Parameters", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(deepLinkParams.toList()) { (key, value) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Row(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    key,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    value,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            RoundedButton(title = "Copy Branch Link", icon = R.drawable.content_copy) {
                // Create Link and copy to clipboard
                val buo = BranchUniversalObject().setTitle(title)
                val lp = LinkProperties()
                    .setFeature("Copy Link Button")
                val url = buo.getShortUrl(context, lp)

                val clipboardManager =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("Branch Link", url)
                clipboardManager.setPrimaryClip(clipData)

                Toast.makeText(context, "Link copied to clipboard", Toast.LENGTH_LONG).show()
            }
        }
    }
}