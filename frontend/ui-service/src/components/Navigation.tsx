"use client"

import {
  BreadcrumbItem,
  Breadcrumbs,
  Button,
  Link,
  Navbar,
  NavbarBrand,
  NavbarContent,
  NavbarItem,
  NavbarMenu,
  NavbarMenuItem,
  NavbarMenuToggle,
} from "@nextui-org/react"
import { usePathname } from "next/navigation"
import { useState } from "react"
import { BrandLogo } from "./BrandLogo"

const menuItems = ["내정보", "홈화면", "수입내역", "수입통계", "지출내역", "지출통계", "지출계획", "로그아웃"]
const navbarItems = [
  { label: "수입", key: "income" },
  { label: "지출", key: "spending" },
  { label: "통계", key: "statistics" },
]

export default function Navigation() {
  const pathname = usePathname()
  const [isMenuOpen, setIsMenuOpen] = useState(false)

  return (
    <div className="mb-8">
      <Navbar isMenuOpen={isMenuOpen} onMenuOpenChange={setIsMenuOpen} isBordered>
        <NavbarContent>
          <NavbarMenuToggle aria-label={isMenuOpen ? "Close menu" : "Open menu"} className="sm:hidden" />
          <NavbarBrand>
            <BrandLogo />
            <p className="font-bold text-inherit">FM</p>
          </NavbarBrand>
        </NavbarContent>

        <NavbarContent className="hidden sm:flex gap-4" justify="center">
          {navbarItems.map(({ key, label }) => (
            <NavbarItem key={key} isActive={pathname.includes(key)}>
              {pathname.includes(key) ? (
                <Link href={`/${key}`} aria-current="page">
                  {label}
                </Link>
              ) : (
                <Link color="foreground" href={`/${key}`}>
                  {label}
                </Link>
              )}
            </NavbarItem>
          ))}
        </NavbarContent>

        <NavbarContent justify="end">
          <NavbarItem className="hidden lg:flex">
            <Link href="#">로그인</Link>
          </NavbarItem>
          <NavbarItem>
            <Button as={Link} color="primary" href="#" variant="flat">
              회원 가입
            </Button>
          </NavbarItem>
        </NavbarContent>

        <NavbarMenu>
          {menuItems.map((item, index) => (
            <NavbarMenuItem key={`${item}-${index}`}>
              <Link color={index === 2 ? "primary" : index === menuItems.length - 1 ? "danger" : "foreground"} className="w-full" href="#" size="lg">
                {item}
              </Link>
            </NavbarMenuItem>
          ))}
        </NavbarMenu>
      </Navbar>
      <div className="flex flex-col flex-wrap mt-4 items-center">
        <Breadcrumbs size="sm">
          <BreadcrumbItem>Home</BreadcrumbItem>
          <BreadcrumbItem>Music</BreadcrumbItem>
          <BreadcrumbItem>Artist</BreadcrumbItem>
          <BreadcrumbItem>Album</BreadcrumbItem>
          <BreadcrumbItem>Song</BreadcrumbItem>
        </Breadcrumbs>
      </div>
    </div>
  )
}
