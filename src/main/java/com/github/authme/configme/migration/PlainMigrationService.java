package com.github.authme.configme.migration;

import com.github.authme.configme.propertymap.KnownProperties;
import com.github.authme.configme.propertymap.PropertyEntry;
import com.github.authme.configme.resource.PropertyResource;

/**
 * Simple migration service that can be extended.
 */
public class PlainMigrationService implements MigrationService {

    @Override
    public boolean checkAndMigrate(PropertyResource resource, KnownProperties knownProperties) {
        return performMigrations(resource, knownProperties) || !containsAllSettings(resource, knownProperties);
    }

    /**
     * Override this method for custom migrations. This method is executed before checking
     * if all settings are present. For instance, you could implement deleting obsolete properties
     * and renamed properties in this method.
     * <p>
     * Note that you do <i>not</i> have to save the resource. The settings manager automatically
     * does this if the migration service returns an according result.
     *
     * @param resource the property resource to check
     * @param knownProperties the property map
     * @return true if a migration was performed and the config should be saved,
     * false if no migration was performed
     */
    protected boolean performMigrations(PropertyResource resource, KnownProperties knownProperties) {
        return false;
    }

    private static boolean containsAllSettings(PropertyResource resource, KnownProperties knownProperties) {
        for (PropertyEntry entry : knownProperties.getEntries()) {
            if (!entry.getProperty().isPresent(resource)) {
                return false;
            }
        }
        return true;
    }

}