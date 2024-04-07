type Props = { children: React.ReactNode }

export default function Container({ children }: Props) {
  return <div className="container mt-4 mx-auto px-4">{children}</div>
}
