package org.motechproject.openmrs.service.impl;

import org.motechproject.openmrs.config.Config;
import org.motechproject.openmrs.domain.GeneratedIdentifier;
import org.motechproject.openmrs.exception.OpenMRSException;
import org.motechproject.openmrs.resource.GeneratedIdentifierResource;
import org.motechproject.openmrs.service.OpenMRSGeneratedIdentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

@Service("identifierService")
public class OpenMRSGeneratedIdentifierServiceImpl implements OpenMRSGeneratedIdentifierService {

    private GeneratedIdentifierResource generatedIdentifierResource;

    private OpenMRSConfigServiceImpl configService;

    @Autowired
    public OpenMRSGeneratedIdentifierServiceImpl(GeneratedIdentifierResource generatedIdentifierResource, OpenMRSConfigServiceImpl configService) {
        this.generatedIdentifierResource = generatedIdentifierResource;
        this.configService = configService;
    }

    @Override
    public GeneratedIdentifier getLatestIdentifier(String configName, String sourceName) {

        try {
            Config config = configService.getConfigByName(configName);

            return generatedIdentifierResource.getGeneratedIdentifier(config, sourceName);
        } catch (HttpStatusCodeException e) {
            throw new OpenMRSException(String.format("Cannot get latest identifier from Generator with name: %s. %s %s", sourceName, e.getMessage(), e.getResponseBodyAsString()), e);
        }
    }

    @Override
    public void setLatestIdentifier(String configName, String sourceName, Long identifier) {
        try {
            Config config = configService.getConfigByName(configName);

            generatedIdentifierResource.setLatestIdentifier(config, sourceName, identifier.toString());
        } catch (HttpStatusCodeException e) {
            throw new OpenMRSException(String.format("Cannot set latest identifier for Generator with name: %s. %s %s", sourceName, e.getMessage(), e.getResponseBodyAsString()), e);
        }
    }
}
