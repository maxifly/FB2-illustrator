package com.maxifly.fb2_illustrator;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.image_xml.model.Image;
import com.maxifly.fb2_illustrator.image_xml.model.Images;
import com.maxifly.fb2_illustrator.image_xml.model.SearchTemplateType;
import com.maxifly.fb2_illustrator.image_xml.model.SearchType;
import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Illustrations;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.fb2_illustrator.model.TemplateType;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by Maximus on 02.05.2016.
 */
public class IllustrationParser {
    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(IllustrationParser.class.getName());

    public Illustrations parseIll(Path illSetPath) throws JAXBException {
        log.debug("Parse illustrations XML: {}", illSetPath);

        // Разберем XML
        JAXBContext jc = JAXBContext.newInstance("com.maxifly.fb2_illustrator.image_xml.model");
        Unmarshaller unmarshaller = jc.createUnmarshaller();


        Images images = (Images) unmarshaller.unmarshal(illSetPath.toFile());

        Illustrations illustrations = new Illustrations();

        String illPath = images.getPath();

        int i = 0;

        for (Image img : images.getImage()) {

            Illustration ill = new Illustration(
                    i,
                    FileSystems.getDefault().getPath(illPath, img.getFile()),
                    img.getDefaultDesc()
            );

            for (SearchTemplateType stt : img.getSearchTemplate()) {
                TemplateType templateType = null;
                SearchType stp = stt.getType();

                switch (stp) {
                    case REGULAR:
                        templateType = TemplateType.regexp;
                        break;
                    case SUBSTRING:
                        templateType = TemplateType.substr;
                        break;
                    default:
                        templateType = TemplateType.substr;
                }

                SearchTemplate_POJO searchTemplate_pojo =
                        new SearchTemplate_POJO(templateType,
                                stt.getTemplate(),
                                stt.getDescription());

                ill.addSearchTempale(searchTemplate_pojo);


            }

            illustrations.addIllustration(ill);
            i++;
        }
        return illustrations;
    }
}
