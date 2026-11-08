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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material.icons.rounded.Http
import androidx.compose.material.icons.rounded.Rule
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.VpnKey
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.kin.athena.presentation.navigation.routes.SettingRoutes
import com.kin.athena.core.utils.extensions.safeNavigate
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
    val currentSettings = settings.settings.value
    val vpnModeSelected = currentSettings.useRootMode != true && currentSettings.useShizukuMode != true
    val selectedMethod = if (vpnModeSelected) "VPN service selected" else "Switch to VPN mode in Firewall Behavior"

    SettingsScaffold(
        settings = settings,
        title = "VPN Service",
        onBackNavClicked = { navController.navigateUp() }
    ) {
        settingsContainer {
            SettingsBox(
                title = "Athena local VPN firewall",
                description = "Routes device traffic through Athena's local VPN interface for firewall and DNS filtering. This is not a commercial VPN tunnel.",
                icon = IconType.VectorIcon(Icons.Rounded.VpnKey),
                actionType = SettingType.TEXT,
                customText = selectedMethod
            )
            SettingsBox(
                title = "Firewall mode",
                description = "Choose the Android VPN firewall mode or review the selected method.",
                icon = IconType.VectorIcon(Icons.Rounded.Security),
                actionType = SettingType.LINK,
                onLinkClicked = { navController.safeNavigate(SettingRoutes.Behavior.route) }
            )
        }

        settingsContainer {
            SettingsBox(
                title = "DNS provider and blocklists",
                description = "Configure Athena's resolver, custom DNS addresses, and DNS blocklists.",
                icon = IconType.VectorIcon(Icons.Rounded.Dns),
                actionType = SettingType.LINK,
                onLinkClicked = { navController.safeNavigate(SettingRoutes.Dns.route) }
            )
            SettingsBox(
                title = "Custom domain rules",
                description = "Manage the existing domain allowlist and blocklist.",
                icon = IconType.VectorIcon(Icons.Rounded.Rule),
                actionType = SettingType.LINK,
                onLinkClicked = { navController.safeNavigate(SettingRoutes.DomainManagement.route) }
            )
            SettingsBox(
                title = "Block unencrypted HTTP",
                description = "Block TCP port 80 while Athena's firewall is active.",
                icon = IconType.VectorIcon(Icons.Rounded.Http),
                actionType = SettingType.SWITCH,
                variable = currentSettings.blockPort80,
                onSwitchEnabled = { enabled ->
                    settings.update(settings.settings.value.copy(blockPort80 = enabled))
                }
            )
        }

        settingsContainer {
            SettingsBox(
                title = "External VPN compatibility",
                description = "Android allows one active VPN per user. Connecting WireGuard or Cloudflare One will replace or conflict with Athena's VPN firewall. Athena does not import WireGuard .conf files or embed Cloudflare WARP.",
                icon = IconType.VectorIcon(Icons.Rounded.Security),
                actionType = SettingType.TEXT,
                customText = "Use one VPN service at a time"
            )
        }
    }
}
