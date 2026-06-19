package filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.substring(contextPath.length());

        boolean halamanLogin = path.equals("/login");
        boolean prosesLogout = path.equals("/logout");
        boolean fileAsset = path.startsWith("/assets/");
        boolean fileReport = path.startsWith("/reports/");
        boolean root = path.equals("/") || path.isEmpty();

        HttpSession session = req.getSession(false);

        boolean sudahLogin = session != null && session.getAttribute("idUser") != null;
        String role = sudahLogin ? (String) session.getAttribute("role") : null;

        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);

        if (halamanLogin || prosesLogout || fileAsset || fileReport || root) {
            chain.doFilter(request, response);
            return;
        }

        if (!sudahLogin) {
            res.sendRedirect(contextPath + "/login");
            return;
        }

        if ("Operator".equals(role)) {
            boolean aksesUserPage = path.equals("/user");
            boolean aksesLaporanUser = path.equals("/laporan") && "user".equals(req.getParameter("action"));

            if (aksesUserPage || aksesLaporanUser) {
                String pesan = URLEncoder.encode("Anda tidak memiliki akses ke halaman tersebut.", StandardCharsets.UTF_8);
                res.sendRedirect(contextPath + "/dashboard?error=" + pesan);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}