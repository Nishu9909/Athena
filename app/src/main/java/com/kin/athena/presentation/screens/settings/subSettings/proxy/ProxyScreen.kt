/*
 * Copyright (C) 2025-2026 Vexzure
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.kin.athena.presentation.screens.settings.subSettings.proxy

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material.icons.rounded.Http
import androidx.compose.material.icons.rounded.Rule
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kin.athena.R
import com.kin.athena.core.utils.extensions.safeNavigate
import com.kin.athena.presentation.navigation.routes.SettingRoutes
import com.kin.athena.presentation.screens.settings.components.IconType
import com.kin.athena.presentation.screens.settings.components.SettingType
import com.kin.athena.presentation.screens.settings.components.SettingsBox
import com.kin.athena.presentation.screens.settings.components.SettingsScaffold
import com.kin.athena.presentation.screens.settings.components.settingsContainer
import com.kin.athena.presentation.screens.settings.viewModel.SettingsViewModel

@Composable
fun ProxyScreen(
    navController: NavController,
    settings: SettingsViewModel,
) {
    val context = LocalContext.current

    SettingsScaffold(
        settings = settings,
        title = stringResource(id = R.string.proxy_title),
        onBackNavClicked = { navController.navigateUp() }
    ) {
        settingsContainer {
            SettingsBox(
                title = "Cloudflare One",
                description = "Open the official Cloudflare One client to enroll or connect.",
                icon = IconType.VectorIcon(Icons.Rounded.VpnKey),
                actionType = SettingType.LINK,
                onLinkClicked = { openCloudflareOne(context) }
            )
            SettingsBox(
                title = "Firewall mode",
                description = "Choose Athena VPN, Root, or Shizuku firewall mode in Behavior settings.",
                icon = IconType.VectorIcon(Icons.Rounded.Security),
                actionType = SettingType.LINK,
                onLinkClicked = { navController.safeNavigate(SettingRoutes.Behavior.route) }
            )
        }

        settingsContainer {
            SettingsBox(
                title = "Block unencrypted HTTP",
                description = "Drops HTTP traffic on port 80 while Athena firewall is active.",
                icon = IconType.VectorIcon(Icons.Rounded.Http),
                actionType = SettingType.SWITCH,
                variable = settings.settings.value.blockPort80,
                onSwitchEnabled = { enabled ->
                    settings.update(settings.settings.value.copy(blockPort80 = enabled))
                }
            )
            SettingsBox(
                title = "DNS providers and filtering",
                description = "Choose a resolver, configure custom DNS, and manage blocklists.",
                icon = IconType.VectorIcon(Icons.Rounded.Dns),
                actionType = SettingType.LINK,
                onLinkClicked = { navController.safeNavigate(SettingRoutes.Dns.route) }
            )
            SettingsBox(
                title = "User domain rules",
                description = "Manage allowlist and blocklist rules with Athena's existing rule engine.",
                icon = IconType.VectorIcon(Icons.Rounded.Rule),
                actionType = SettingType.LINK,
                onLinkClicked = { navController.safeNavigate(SettingRoutes.DomainManagement.route) }
            )
        }

        settingsContainer {
            SettingsBox(
                title = "WireGuard profiles",
                description = "Athena does not yet import or run WireGuard .conf tunnels. Use the official WireGuard app; Android permits one active VPN at a time.",
                icon = IconType.VectorIcon(Icons.Rounded.VpnKey),
                actionType = SettingType.TEXT,
                customText = "Not available"
            )
            SettingsBox(
                title = "UserScripts",
                description = "Arbitrary JavaScript execution is not supported. Use the declarative domain allow/block rules above.",
                icon = IconType.VectorIcon(Icons.Rounded.Rule),
                actionType = SettingType.TEXT,
                customText = "Not available"
            )
        }
    }
}

private fun openCloudflareOne(context: Context) {
    val launchIntent = context.packageManager.getLaunchIntentForPackage("com.cloudflare.onedotone")
    try {
        if (launchIntent != null) {
            context.startActivity(launchIntent)
        } else {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://one.dash.cloudflare.com"))
            )
        }
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, "Install the Cloudflare One app to connect to WARP.", Toast.LENGTH_LONG).show()
    }
}
