/*
 * SonarQube
 * Copyright (C) 2009-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.platform.db.migration.version.v74;

import java.sql.SQLException;
import org.sonar.api.config.Configuration;
import org.sonar.db.Database;
import org.sonar.server.platform.db.migration.SupportsBlueGreen;

@SupportsBlueGreen
public class PopulateMainComponentUuidColumnsToCeActivity extends PopulateMainComponentUuidColumnsToCeTable {
  private final Configuration configuration;

  public PopulateMainComponentUuidColumnsToCeActivity(Database db, Configuration configuration) {
    super(db, "ce_activity");
    this.configuration = configuration;
  }

  @Override
  protected void execute(Context context) throws SQLException {
    if (configuration.getBoolean("sonar.sonarcloud.enabled").orElse(false)) {
      // data migration will be done in background so that interruption of service
      // is reduced during upgrade
      return;
    }

    super.execute(context);
  }
}
