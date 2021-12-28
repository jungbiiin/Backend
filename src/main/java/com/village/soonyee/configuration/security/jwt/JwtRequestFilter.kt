package com.village.soonyee.configuration.security.jwt

import com.village.soonyee.configuration.security.auth.MyUserDetailsService
import com.village.soonyee.exception.ErrorCode
import com.village.soonyee.exception.exception.InvalidTokenException
import com.village.soonyee.exception.exception.UserNotFoundException
import io.jsonwebtoken.JwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
        private val myUserDetailsService: MyUserDetailsService,
        private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, filterChain: FilterChain) {
        // Access Token이 null이면 검증할 필요가 없다.
        req.getHeader("Authorization")?.let(::accessTokenExtractEmail)?.also {
            registerUserinfoInSecurityContext(it, req)
        }
        filterChain.doFilter(req, res)
    }

    private fun accessTokenExtractEmail(accessToken: String): String {
        return try {
            jwtUtil.getUserEmail(accessToken)
        } catch (e: JwtException) {
            throw InvalidTokenException("Invalid access token", ErrorCode.INVALID_TOKEN)
        } catch (e: IllegalArgumentException) {
            throw InvalidTokenException("Invalid access token", ErrorCode.INVALID_TOKEN)
        }
    }

    private fun registerUserinfoInSecurityContext(userEmail: String, req: HttpServletRequest) {
        try {
            val userDetails = myUserDetailsService.loadUserByUsername(userEmail)
            SecurityContextHolder.getContext().apply {
                authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities).apply {
                    details = WebAuthenticationDetailsSource().buildDetails(req)
                }
            }
        } catch (e: NullPointerException) {
            throw UserNotFoundException("Can't find user", ErrorCode.USER_NOT_FOUND)
        }
    }
}