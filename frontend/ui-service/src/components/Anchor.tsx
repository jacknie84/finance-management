import { LinkIcon } from "@nextui-org/shared-icons"
import { linkAnchorClasses } from "@nextui-org/theme"
import { forwardRef } from "react"

import { LinkProps as UIProps, useLink } from "@nextui-org/react"
import { default as NextLink, LinkProps as NextProps } from "next/link"

const Anchor = forwardRef<HTMLAnchorElement, UIProps & NextProps>((props, ref) => {
  const {
    children,
    showAnchorIcon,
    anchorIcon = <LinkIcon className={linkAnchorClasses} />,
    getLinkProps,
  } = useLink({
    ...props,
    ref,
  })

  return (
    <NextLink href="#" {...getLinkProps()}>
      <>
        {children}
        {showAnchorIcon && anchorIcon}
      </>
    </NextLink>
  )
})

Anchor.displayName = "Anchor"

export default Anchor
