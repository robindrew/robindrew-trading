package com.robindrew.taskmanager.servlet;

import com.robindrew.common.http.servlet.IHttpRequest;
import com.robindrew.common.http.servlet.IHttpResponse;
import com.robindrew.spring.web.template.AbstractServiceServlet;
import com.robindrew.spring.web.template.TemplateResource;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFileManager;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceProviderManager;
import com.robindrew.trading.price.candle.format.ptf.source.file.IPtfFileManager;
import jakarta.servlet.annotation.WebServlet;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

// Mapped to "/Home" rather than "/" - Spring Boot's DispatcherServlet already owns the bare "/"
// mapping, so a plain @WebServlet there never actually receives requests (its own mapping fails to
// register, silently, since the pattern is already claimed) - see HomeController, which forwards
// "/" to this servlet server-side so the page is still reachable at the site root.
@WebServlet(urlPatterns = "/Home")
@TemplateResource("templates/Home.html")
public class HomeServlet extends AbstractServiceServlet {

    @Autowired
    private IPtfFileManager ptfFileManager;

    @Autowired
    private IPcfFileManager pcfFileManager;

    @Override
    protected void populate(Map<String, Object> dataMap) {
        super.populate(dataMap);
        dataMap.put("ptfFormatName", ptfFileManager.getFormat().name());
        dataMap.put("ptfRootDirectory", ptfFileManager.getRootDirectory());
        dataMap.put("ptfProviders", ptfFileManager.getProviders());
        dataMap.put("pcfFileManager", pcfFileManager);
        dataMap.put("pcfFormatName", pcfFileManager.getFormat().name());
        dataMap.put("pcfRootDirectory", pcfFileManager.getRootDirectory());
        dataMap.put("pcfProviders", pcfFileManager.getProviders());
        dataMap.put("pcfHasUnconvertedInstruments", hasUnconvertedInstruments());
    }

    private boolean hasUnconvertedInstruments() {
        for (IPtfSourceProviderManager provider : ptfFileManager.getProviders()) {
            for (IInstrument instrument : provider.getInstruments()) {
                if (!pcfFileManager.hasInstrument(provider.getProvider(), instrument)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap) {}
}
